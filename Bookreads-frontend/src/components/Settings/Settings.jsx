import { useState, useEffect } from 'react'
import { useAuth } from '../Auth/AuthContext'
import { useError } from '../Error/ErrorContext'
import SettingButton from './SettingButton'
import ChangeField from './ChangeField'
import ChangePassword from './ChangePassword'
import DeleteAccount from './DeleteAccount'
import userService from '../../services/users'

const SettingsValues = Object.freeze({
    CHANGE_USERNAME: 'Username Change',
    CHANGE_EMAIL: 'Email Change',
    CHANGE_PASSWORD: 'Password Change',
    DELETE_ACCOUNT: 'Delete Account'
})

const Settings = () => {
    const [currentSetting, setCurrentSetting] = useState(SettingsValues.CHANGE_USERNAME)
    const [currentUser, setCurrentUser] = useState({'username': ' ','email': '','id': -1})
    const { user, logout } = useAuth()
    const { triggerError } = useError()

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
            triggerError(error.response.data.username)
        }
    }

    const emailChange = async newEmail => {
        try {
            const updatedUser = await userService.updateUser(currentUser.id, { ...currentUser, 'email': newEmail})
            setCurrentUser(updatedUser)
        } catch (error) {
            triggerError(error.response.data.email)
        }
    }

    const passwordChange = async (oldPassword, newPassword) => {
        try {
            const updatedUser = await userService.updatePassword(currentUser.id, { oldPassword, newPassword })
            setCurrentUser(updatedUser)
        } catch (error) {
            let passwordError;
            if (error.response.data.oldPassword) {
                if (error.response.data.newPassword) {
                    passwordError = error.response.data.oldPassword + '. ' + error.response.data.newPassword
                } else {
                    passwordError = error.response.data.oldPassword
                }
            } else {
                passwordError = error.response.data.newPassword
            }
            triggerError(passwordError)
        }
    }

    const deleteAccount = async () => {
        try {
            await userService.deleteUser(currentUser.id)
            logout()
        } catch (error) {
            console.log(error)
        }
    }

    const handleSettingChange = setting => {
        setCurrentSetting(setting)
    }

    return (
        <div>
            <h1 className='m-10 text-4xl text-center font-semibold'>Settings</h1>
            <div className='flex justify-center text-center'>
                <SettingButton currentSetting={currentSetting} setting={SettingsValues.CHANGE_USERNAME} handleSettingChange={handleSettingChange} />
                <SettingButton currentSetting={currentSetting} setting={SettingsValues.CHANGE_EMAIL} handleSettingChange={handleSettingChange} />
                <SettingButton currentSetting={currentSetting} setting={SettingsValues.CHANGE_PASSWORD} handleSettingChange={handleSettingChange} />
                <SettingButton currentSetting={currentSetting} setting={SettingsValues.DELETE_ACCOUNT} handleSettingChange={handleSettingChange} />
            </div>

            <div className='mt-10 flex justify-center'>
                {currentSetting === SettingsValues.CHANGE_USERNAME && <ChangeField fieldName={'Username'} fieldValue={currentUser.username} fieldChange={usernameChange} />}
                {currentSetting === SettingsValues.CHANGE_EMAIL && <ChangeField fieldName={'Email'} fieldValue={currentUser.email} fieldChange={emailChange} />}
                {currentSetting === SettingsValues.CHANGE_PASSWORD && <ChangePassword passwordChange={passwordChange} />}
                {currentSetting === SettingsValues.DELETE_ACCOUNT && <DeleteAccount deleteAccount={deleteAccount} />}
            </div>
        </div>
    )
}

export default Settings