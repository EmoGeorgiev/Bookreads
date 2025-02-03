import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import userService from '../services/users'

const UserList = () => {
    const [users, setUsers] = useState([])
    const [filteredUsers, setFilteredUsers] = useState([])
    const [query, setQuery] = useState('')
    const navigate = useNavigate()
    
    useEffect(() => {
        const getUsers = async () => {
            try {
                const newUsers = await userService.getUsers()
                setUsers(newUsers)
                setFilteredUsers(newUsers)
            } catch (error) {
                console.log(error)
            }
        }

        getUsers()
    }, [])

    const handleQueryChange = e => {
        const newQuery = e.target.value.toLowerCase()
        setQuery(newQuery)
        setFilteredUsers(users.filter(user => user.username.toLowerCase().includes(newQuery)))
    }

    const handleClear = e => {
        e.preventDefault()
        setQuery('')
        setFilteredUsers(users)
    }

    const handleViewBooks = userId => {
        navigate(`/users/${userId}/books`)
    }

    return (
        <div>
            <h1>Users</h1>
            <p>Search by username:</p>
            <input
                type='text'
                value={query}
                name='query'
                onChange={handleQueryChange}
            />
            <button onClick={handleClear}>Clear</button>

            {filteredUsers.length === 0
                ? <div>
                    <p>No results. Nobody with the name &quot;{query}&quot; has signed up on Bookreads</p>
                </div>
                : <table>
                    <thead>
                        <tr>
                            <th>Username</th>
                            <th>Email</th>
                        </tr>
                    </thead>
                    <tbody>
                        {filteredUsers.map(user => <tr key={user.id}>
                            <td>{user.username}</td>
                            <td>{user.email}</td>
                            <td>
                                <button onClick={() => handleViewBooks(user.id)}>
                                    View Books
                                </button>
                            </td>
                        </tr>)}
                    </tbody>
                </table>}
        </div>
    )
}

export default UserList