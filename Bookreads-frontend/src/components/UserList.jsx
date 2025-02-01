import { useState, useEffect } from 'react'

const testUsers = [
    { id: 1, username: 'james.smith', email: 'james.smith@gmail.com' },
    { id: 2, username: 'susan.johnson', email: 'susan.johnson@hotmail.com' },
    { id: 3, username: 'michael.williams', email: 'michael.williams@yahoo.com' },
    { id: 4, username: 'emily.brown', email: 'emily.brown@outlook.com' },
    { id: 5, username: 'david.jones', email: 'david.jones@icloud.com' },
    { id: 6, username: 'laura.miller', email: 'laura.miller@gmail.com' },
    { id: 7, username: 'robert.davis', email: 'robert.davis@yahoo.com' },
    { id: 8, username: 'lisa.garcia', email: 'lisa.garcia@hotmail.com' },
    { id: 9, username: 'kevin.martinez', email: 'kevin.martinez@outlook.com' },
    { id: 10, username: 'isabella.lee', email: 'isabella.lee@gmail.com' }
]

const UserList = () => {
    const [users, setUsers] = useState(testUsers)
    const [filteredUsers, setFilteredUsers] = useState(testUsers)
    const [query, setQuery] = useState('')
    
    const getUsers = async () => {

    }

    const handleQueryChange = (e) => {
        const newQuery = e.target.value.toLowerCase()
        setQuery(newQuery)
        setFilteredUsers(users.filter(user => user.username.toLowerCase().includes(newQuery)))
    }

    const handleClear = (e) => {
        e.preventDefault()
        setQuery('')
        setFilteredUsers(users)
    }

    const handleViewBooks = () => {

    }

    return (
        <div>
            <h1>Users</h1>
            <input
                type='text'
                value={query}
                onChange={handleQueryChange}
            />
            <button onClick={handleClear}>Clear</button>

            {filteredUsers.length === 0
                ? <div>
                    <p>No results. Nobody with the name &quot;{query}&quot; has signed up on Bookreads</p>
                </div>
                : <table>
                    <thead>
                        <th>
                            <td>Username</td>
                            <td>Email</td>
                        </th>
                    </thead>
                    <tbody>
                        {filteredUsers.map(user => <tr key={user.id}>
                            <td>{user.username}</td>
                            <td>{user.email}</td>
                            <td>
                                <button onClick={handleViewBooks}>
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