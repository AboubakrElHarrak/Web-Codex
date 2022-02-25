package com.codex.machina.ex.homini.service;

import com.codex.machina.ex.homini.entity.Article;
import com.codex.machina.ex.homini.entity.Rating;
import com.codex.machina.ex.homini.repository.ArticleRepository;
import com.codex.machina.ex.homini.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationServiceImplementation implements RecommendationService
{
    private Map<String, HashMap<Long, Double>> inputData;
    private Map<String, HashMap<Long, Double>> outputData;
    private Map<Long, HashMap<Long, Double>> differenceMat = new HashMap<>();
    private Map<Long, HashMap<Long, Integer>> frequencyMat = new HashMap<>();
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<Long> getPredictions(String username)
    {
        fillInputData();
        buildDiffAndFreqMat(inputData);
        predict(inputData, username);
        int numberOfRecs = isRelevant(username);
        if(outputData  == null  || numberOfRecs == 0)
        {
            return null;
        }
        List<Long> predictionIds = new ArrayList<>();
        int order = 1;
        for(int i = 1; i <= numberOfRecs; i++)
        {
            if(!inputData.get(username).containsKey(Long.valueOf(i)))
            {
                predictionIds.add(select(outputData.get(username).values(), order));
                order++;
            }
        }
        return predictionIds;
    }

    private void fillInputData()
    {
        Double avgTimeSpent = ratingRepository.getAvgTimeSpent().get(0);
        List<Rating> ratings = ratingRepository.findAllByOrderByUser();
        inputData = new HashMap<>();
        for(Rating rating : ratings)
        {
            String username = rating.getUser().getUsername();
            Double metric = rating.getRating() / avgTimeSpent;
            if(inputData.containsKey(username))
            {
                Map<Long, Double> info = inputData.get(username);
                info.put(rating.getArticle().getArticleId(),metric);
            }
            else
            {
                HashMap<Long,Double> info = new HashMap<>();
                info.put(rating.getArticle().getArticleId(), metric);
                inputData.put(username, info);
            }
        }
    }

    private void buildDiffAndFreqMat(Map<String, HashMap<Long, Double>> inputData)
    {
        for(HashMap<Long, Double> user : inputData.values())
        {
            for(Map.Entry<Long, Double> entry1 : user.entrySet())
            {
                if(!differenceMat.containsKey(entry1.getKey()))
                {
                    differenceMat.put(entry1.getKey(), new HashMap<Long, Double>());
                    frequencyMat.put(entry1.getKey(), new HashMap<Long, Integer>());
                }
                for(Map.Entry<Long, Double> entry2 : user.entrySet())
                {
                    int oldCount = 0;
                    if(frequencyMat.get(entry1.getKey()).containsKey(entry2.getKey()))
                    {
                        oldCount = frequencyMat.get(entry1.getKey()).get(entry2.getKey()).intValue();
                    }
                    double oldDiff = 0.0;
                    if(differenceMat.get(entry1.getKey()).containsKey(entry2.getKey()))
                    {
                        oldDiff = differenceMat.get(entry1.getKey()).get(entry2.getKey()).doubleValue();
                    }
                    double observedDiff = entry1.getValue() - entry2.getValue();
                    frequencyMat.get(entry1.getKey()).put(entry2.getKey(), oldCount + 1);
                    differenceMat.get(entry1.getKey()).put(entry2.getKey(), oldDiff + observedDiff);
                }
                for(Long i : differenceMat.keySet())
                {
                    for(Long j : differenceMat.get(i).keySet())
                    {
                        double oldValue = differenceMat.get(i).get(j).doubleValue();
                        int count = frequencyMat.get(i).get(j).intValue();
                        differenceMat.get(i).put(j, oldValue / count);
                    }
                }
            }
        }
    }
    
    private void predict(Map<String, HashMap<Long, Double>> inputData, String username)
    {
        HashMap<Long, Double> userPrediction = new HashMap<>();
        HashMap<Long, Integer> userFrequency = new HashMap<>();
        for(Long i : differenceMat.keySet())
        {
            userFrequency.put(i, 0);
            userPrediction.put(i, 0.0);
        }
        HashMap<Long, Double> entry = inputData.containsKey(username) ? inputData.get(username) : null;
        if(entry == null)
        {
            outputData = null;
        }
        else
        {
            for(Long i : entry.keySet())
            {
                for(Long j : differenceMat.keySet())
                {
                    try
                    {
                        double predictedValue = differenceMat.get(j).get(i).doubleValue() + entry.get(i).doubleValue();
                        double finalValue = predictedValue * frequencyMat.get(j).get(i).intValue();
                        userPrediction.put(j, userPrediction.get(j) + finalValue);
                        userFrequency.put(j, userFrequency.get(j) + frequencyMat.get(j).get(i).intValue());
                    }
                    catch(NullPointerException e)
                    {
                    }
                }
            }
            HashMap<Long, Double> clean = new HashMap<>();
            for(Long i : userPrediction.keySet())
            {
                if(userFrequency.get(i) > 0)
                {
                    clean.put(i, userPrediction.get(i).doubleValue() / userFrequency.get(i).intValue());
                }
            }
            for(Article article : articleRepository.findAll())
            {
                long id = article.getArticleId();
                if(entry.containsKey(id))
                {
                    clean.put(id, entry.get(id));
                }
                else if(!clean.containsKey(id))
                {
                    clean.put(id, -1.0);
                }
            }
            outputData = new HashMap<>();
            outputData.put(username, clean);

        }
    }
    private int isRelevant(String username)
    {
        int numberOfRecs = 0;
        int numberOfRatings = inputData.get(username).keySet().size();
        for(Double rec : outputData.get(username).values())
        {
            if (rec != -1.0)
            {
                numberOfRecs ++;
            }
        }
        if(numberOfRecs - numberOfRatings >= 5 )
        {
            return numberOfRecs - numberOfRatings <= 25 ? numberOfRecs : 25;
        }
        return 0;
    }
    private Long select(Collection<Double> values, int k)
    {
        List<Double> list = new ArrayList<>(values);
        List<Double> immutableList = new ArrayList<>(values);
        for(int i = 1; i < k; i++)
        {
            int maxIndex = i;
            double maxValue = list.get(i);
            for(int j = i + 1; j < list.size(); i++)
            {
                if(list.get(j) > maxValue)
                {
                    maxIndex = j;
                    maxValue = list.get(j);
                }
            }
            double temp = list.get(i);
            list.set(i, list.get(maxIndex));
            list.set(maxIndex, temp);
        }
        return Long.valueOf(immutableList.indexOf(list.get(k)));
    }
}
