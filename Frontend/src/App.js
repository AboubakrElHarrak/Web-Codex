import './App.css';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import HomePage from './homePage/HomePage';
import FormPage from './formPage/FormPage';
function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<HomePage/>}/>
          <Route path="/form" element={<FormPage/>}/>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
