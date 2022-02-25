package com.codex.machina.ex.homini.service;

import java.util.List;

public interface RecommendationService
{
    List<Long> getPredictions(String username);
}
