import { useState, useEffect } from 'react'
import UsernameSearch from './UsernameSearch'
import UserTable from './UserTable'
import userService from '../../services/users'

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
            <h1 className='m-10 text-4xl text-center font-semibold'>Users</h1>
            <UsernameSearch query={query} handleQueryChange={handleQueryChange} handleClear={handleClear} />
            
            {filteredUsers.length === 0 ? 
                <div className='mt-16 text-xl text-center text-red-600 font-mono'>
                    <p className='m-4'>
                        No results.
                    </p>
                    <p>
                        Nobody with the name &quot;{query}&quot; has signed up on Bookreads.
                    </p>
                </div> : 
                <UserTable users={filteredUsers} />}
        </div>
    )
}

export default UserList