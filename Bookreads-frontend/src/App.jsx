import Navigation from './components/Navigation'
import ProtectedRoute from './components/ProtectedRoute'
import LoginForm from './components/LoginForm'
import SignUpForm from './components/SignUpForm'
import Home from './components/Home'
import BookList from './components/BookList'
import AddBook from './components/AddBook'
import UserList from './components/UserList'
import Settings from './components/Settings'
import { Routes, Route } from 'react-router-dom'

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
