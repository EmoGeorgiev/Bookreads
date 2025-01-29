import Navigation from './components/Navigation'
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
        <Route path='/' element={<Home />} />
        <Route path='/books' element={<BookList userId={id} />} />
        <Route path='/users' element={<UserList />} />
        <Route path='/settings' element={<Settings />} />
      </Routes>
    </>
  )
}

export default App
