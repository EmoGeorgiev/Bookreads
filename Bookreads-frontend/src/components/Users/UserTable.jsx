import { useNavigate } from 'react-router-dom'

const UserTable = ({ users }) => {
    const navigate = useNavigate()

    const handleViewBooks = userId => {
        navigate(`/users/${userId}/books`)
    }
    
    return (
        <table className='mt-16 min-w-full'>
            <thead>
                <tr>
                    <th className='py-4 text-2xl font-mono'>Username</th>
                    <th className='py-4 text-2xl font-mono'>Email</th>
                </tr>
            </thead>
            <tbody>
                {users.map(user => 
                    <tr key={user.id}>
                        <td className='px-10 py-2 text-lg text-center font-mono'>{user.username}</td>
                        <td className='px-10 py-2 text-lg text-center font-mono'>{user.email}</td>
                        <td className='px-10 py-2 text-lg'>
                            <button className='w-32 p-1.5 bg-black text-white font-mono hover:bg-neutral-700 rounded-4xl' onClick={() => handleViewBooks(user.id)}>
                                View Books
                            </button>
                        </td>
                    </tr>)}
            </tbody>
        </table>
    )
}

export default UserTable