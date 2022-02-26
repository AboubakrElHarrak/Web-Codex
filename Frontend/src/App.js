import './App.css';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import HomePage from './homePage/HomePage';
import FormPage from './formPage/FormPage';
import Admin_dash_page from './admin_dash/Admin_dash_page';
function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<HomePage/>}/>
          <Route path="/form" element={<FormPage/>}/>
          <Route exact path="/dash" element={<Admin_dash_page/>}/>
        </Routes>
      </Router>
    </div>
  );
}

export default App;
