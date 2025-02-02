import { useState } from 'react'
import { useAuth } from './AuthContext'

const Settings = () => {
    const [newUsername, setNewUsername] = useState('')
    const [oldPassword, setOldPassword] = useState('')
    const [newPassword, setNewPassword] = useState('')

    const { user } = useAuth()

    const handleUsernameChange = (e) => {
        e.preventDefault()
    }

    const handlePasswordChange = (e) => {
        e.preventDefault()
    }

    return (
        <div>
            <h1>Settings</h1>
            <div>
                <h2>Username change</h2>
                <form onSubmit={handleUsernameChange}>
                    <div>
                        <p>Current username : {user.username}</p>
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

            <div>
                <h2>Password change</h2>
                <form onSubmit={handlePasswordChange}>
                    <div>
                    <input
                            type='password'
                            value={oldPassword}
                            name='oldPassword'
                            onChange={({ target }) => setOldPassword(target.value)}
                            placeholder='Old Password'
                    />     
                    </div>
                    <div>
                        <input
                                type='password'
                                value={newPassword}
                                name='newPassword'
                                onChange={({ target }) => setNewPassword(target.value)}
                                placeholder='New Password'
                        />    
                    </div>
                    <div>
                        <button>
                            Change Password
                        </button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default Settings