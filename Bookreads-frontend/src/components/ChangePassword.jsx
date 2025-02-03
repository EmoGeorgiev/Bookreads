import { useState } from 'react'

const ChangePassword = ({ passwordChange }) => {
    const [oldPassword, setOldPassword] = useState('')
    const [newPassword, setNewPassword] = useState('')

    const handlePasswordChange = e => {
        e.preventDefault()
        passwordChange(oldPassword, newPassword)
        setOldPassword('')
        setNewPassword('')
    }

    return (
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
    )
}

export default ChangePassword