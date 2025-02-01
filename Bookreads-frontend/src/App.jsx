import Navigation from './components/Navigation'
import ProtectedRoute from './components/ProtectedRoute'
import LoginForm from './components/LoginForm'
import SignupForm from './components/SignupForm'
import Home from './components/Home'
import BookList from './components/BookList'
import UserList from './components/UserList'
import Settings from './components/Settings'
import { Routes, Route } from 'react-router-dom'

const App = () => {
  const id = 1

  return (
    <>
      <Navigation />
      <Routes>
        <Route path='/login' element={<LoginForm />} />
        <Route path='/signup' element={<SignupForm />} />
        <Route path='/' element={<ProtectedRoute><Home /></ProtectedRoute>} />
        <Route path='/books' element={<ProtectedRoute><BookList userId={id} /></ProtectedRoute>} />
        <Route path='/users' element={<ProtectedRoute><UserList /></ProtectedRoute>} />
        <Route path='/settings' element={<ProtectedRoute><Settings /></ProtectedRoute>} />
      </Routes>
    </>
  )
}

export default App
