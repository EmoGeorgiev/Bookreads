import { Routes, Route } from 'react-router-dom'
import Navigation from './components/Common/Navigation'
import ProtectedRoute from './components/Auth/ProtectedRoute'
import LoginForm from './components/Auth/LoginForm'
import SignUpForm from './components/Auth/SignUpForm'
import Home from './components/Common/Home'
import BookList from './components/Books/BookList'
import AddBook from './components/Books/AddBook'
import UserList from './components/Users/UserList'
import Settings from './components/Settings/Settings'

const App = () => {
  return (
    <>
      <Navigation />
      <Routes>
        <Route path='/login' element={<LoginForm />} />
        <Route path='/signup' element={<SignUpForm />} />
        <Route path='/' element={<ProtectedRoute><Home /></ProtectedRoute>} />
        <Route path='/users/:userId/books' element={<ProtectedRoute><BookList /></ProtectedRoute>} />
        <Route path='books/add' element={<ProtectedRoute><AddBook /></ProtectedRoute>} />
        <Route path='/users' element={<ProtectedRoute><UserList /></ProtectedRoute>} />
        <Route path='/settings' element={<ProtectedRoute><Settings /></ProtectedRoute>} />
      </Routes>
    </>
  )
}

export default App
