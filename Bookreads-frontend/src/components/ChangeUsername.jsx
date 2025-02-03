import { useState } from 'react'

const ChangeUsername = ({ username, usernameChange }) => {
    const [newUsername, setNewUsername] = useState('')

    const handleUsernameChange = e => {
        e.preventDefault()

        try {
            usernameChange(newUsername)
            setNewUsername('')
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <div>
            <h2>Username change</h2>
            <form onSubmit={handleUsernameChange}>
                <div>
                    <p>Current username : {username}</p>
                    <input
                        type='text'
                        value={newUsername}
                        name='username'
                        onChange={({ target }) => setNewUsername(target.value)}
                        placeholder='Username'
                    />
                </div>
                <div>
                    <button>
                        Save
                    </button>
                </div>
            </form>
        </div>
    )
}

export default ChangeUsername