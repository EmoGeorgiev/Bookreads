import { Link } from 'react-router-dom'
import { useAuth } from './AuthContext'

const Navigation = () => {
    const { logout, isAuthenticated } = useAuth()
    return (
        <>
            {isAuthenticated && 
                <nav>
                    <Link to='/'>Home</Link>
                    <Link to='/books'>My Books</Link>
                    <Link to='/users'>Users</Link>
                    <Link to='/settings'>Settings</Link>
                    <button onClick={logout}>Log out</button>
                </nav>
            }   
        </>
    )
}

export default Navigation