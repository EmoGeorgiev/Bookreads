import { useState, useEffect } from 'react'
import { useAuth } from './AuthContext'
import userService from '../services/users'

const Settings = () => {
    const [newUsername, setNewUsername] = useState('')
    const [newEmail, setNewEmail] = useState('')
    const [oldPassword, setOldPassword] = useState('')
    const [newPassword, setNewPassword] = useState('')
    const [currentUser, setCurrentUser] = useState({'username': ' ','email': '','id': -1})
    const { user } = useAuth()

    useEffect(()=> { 
        const getUser = async () => {
            try {
                const newUser = await userService.getUser(user.id)
                setCurrentUser(newUser)
            } catch (error) {
                console.log(error)
            }
        }

        getUser()
    }, [user.id])

    const handleUsernameChange = async (e) => {
        e.preventDefault()

        try {
            const updatedUser = await userService.updateUser(currentUser.id, { ...currentUser, 'username': newUsername})
            setCurrentUser(updatedUser)
            setNewUsername('')
        } catch (error) {
            console.log(error)
        }
    }

    const handleEmailChange = async (e) => {
        e.preventDefault()

        try {
            const updatedUser = await userService.updateUser(currentUser.id, { ...currentUser, 'email': newEmail})
            setCurrentUser(updatedUser)
            setNewEmail('')
        } catch (error) {
            console.log(error)
        }
    }

    const handlePasswordChange = async (e) => {
        e.preventDefault()

        try {
            const updatedUser = await userService.updatePassword(currentUser.id, oldPassword, newPassword)
            setCurrentUser(updatedUser)
            setOldPassword('')
            setNewPassword('')
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <div>
            <h1>Settings</h1>
            <div>
                <h2>Username change</h2>
                <form onSubmit={handleUsernameChange}>
                    <div>
                        <p>Current username : {currentUser.username}</p>
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
                <h2>Email change</h2>
                <form onSubmit={handleEmailChange}>
                    <div>
                        <p>Current username : {currentUser.email}</p>
                        <input
                            type='text'
                            value={newEmail}
                            name='email'
                            onChange={({ target }) => setNewEmail(target.value)}
                            placeholder='Email'
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