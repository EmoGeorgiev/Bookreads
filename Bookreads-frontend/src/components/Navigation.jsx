import { Link } from 'react-router-dom'
import { useAuth } from './AuthContext'

const Navigation = () => {
    const { user, logout, isAuthenticated } = useAuth()
    return (
        <>
            {isAuthenticated && 
                <nav>
                    <Link to='/'>Home</Link>
                    <Link to={`/users/${user.id}/books`}>My Books</Link>
                    <Link to='/books/add'>Add Book</Link>
                    <Link to='/users'>Users</Link>
                    <Link to='/settings'>Settings</Link>
                    <button onClick={logout}>Log out</button>
                </nav>
            }   
        </>
    )
}

export default Navigation