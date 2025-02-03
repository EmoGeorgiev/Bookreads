import { useState, useEffect } from 'react'
import UserTable from './UserTable'
import userService from '../services/users'
import UsernameSearch from './UsernameSearch'

const UserList = () => {
    const [users, setUsers] = useState([])
    const [filteredUsers, setFilteredUsers] = useState([])
    const [query, setQuery] = useState('')
    
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

    return (
        <div>
            <h1>Users</h1>
            <UsernameSearch query={query} handleQueryChange={handleQueryChange} handleClear={handleClear} />
            
            {filteredUsers.length === 0 ? 
                <p>No results. Nobody with the name &quot;{query}&quot; has signed up on Bookreads</p> : 
                <UserTable users={filteredUsers} />}
        </div>
    )
}

export default UserList