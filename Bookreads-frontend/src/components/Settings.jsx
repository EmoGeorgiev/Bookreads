import { useState, useEffect } from 'react'
import { useAuth } from './AuthContext'
import ChangeUsername from './ChangeUsername'
import ChangeEmail from './ChangeEmail'
import ChangePassword from './ChangePassword'
import userService from '../services/users'

const Settings = () => {
    const [currentUser, setCurrentUser] = useState({'username': ' ','email': '','id': -1})
    const { user } = useAuth()

    useEffect(() => { 
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

    const usernameChange = async newUsername => {
        try {
            const updatedUser = await userService.updateUser(currentUser.id, { ...currentUser, 'username': newUsername})
            setCurrentUser(updatedUser)
        } catch (error) {
            console.log(error)
        }
    }

    const emailChange = async newEmail => {
        try {
            const updatedUser = await userService.updateUser(currentUser.id, { ...currentUser, 'email': newEmail})
            setCurrentUser(updatedUser)
        } catch (error) {
            console.log(error)
        }
    }

    const passwordChange = async (oldPassword, newPassword) => {
        try {
            const updatedUser = await userService.updatePassword(currentUser.id, oldPassword, newPassword)
            setCurrentUser(updatedUser)
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <div>
            <h1>Settings</h1>
            <ChangeUsername username={currentUser.username} usernameChange={usernameChange} />
            <ChangeEmail email={currentUser.email} emailChange={emailChange} />
            <ChangePassword passwordChange={passwordChange} />
        </div>
    )
}

export default Settings