import { useNavigate } from 'react-router-dom'

const UserTable = ({ users }) => {
    const navigate = useNavigate()

    const handleViewBooks = userId => {
        navigate(`/users/${userId}/books`)
    }
    
    return (
        <table>
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Email</th>
                </tr>
            </thead>
            <tbody>
                {users.map(user => <tr key={user.id}>
                    <td>{user.username}</td>
                    <td>{user.email}</td>
                    <td>
                        <button onClick={() => handleViewBooks(user.id)}>
                            View Books
                        </button>
                    </td>
                </tr>)}
            </tbody>
        </table>
    )
}

export default UserTable