import ReactDom from 'react-dom/client'
import './index.css'
import { BrowserRouter as Router } from 'react-router-dom'
import AuthProvider from './components/Auth/AuthProvider.jsx'
import ErrorProvider from './components/Error/ErrorProvider.jsx'
import App from './App.jsx'

ReactDom.createRoot(document.getElementById('root')).render(
    <Router>
        <ErrorProvider>
            <AuthProvider>
                <App />
            </AuthProvider>
        </ErrorProvider>
    </Router>
)
