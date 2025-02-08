import { Link } from 'react-router-dom'
import { useAuth } from '../Auth/AuthContext'

const Navigation = () => {
    const { user, logout, isAuthenticated } = useAuth()
    return (
        <>
            {isAuthenticated && 
                <nav className='top-0 sticky bg-black text-white p-3'>
                    <div className='flex justify-center space-x-14'>
                        <Link className='hover:text-gray-400 transition duration-300' to='/'>Home</Link>
                        <Link className='hover:text-gray-400 transition duration-300' to={`/users/${user.id}/books`}>My Books</Link>
                        <Link className='hover:text-gray-400 transition duration-300' to='/users'>Users</Link>
                        <Link className='hover:text-gray-400 transition duration-300' to='/settings'>Settings</Link>
                        <button className='hover:text-gray-400 transition duration-300' onClick={logout}>
                            Log out
                        </button>
                    </div>
                </nav>
            }   
        </>
    )
}

export default Navigation