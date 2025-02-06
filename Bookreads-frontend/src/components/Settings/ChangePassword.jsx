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
            <h2 className='m-5 text-2xl text-center font-mono font-bold'>Password Change</h2>
                <div className='flex flex-col items-center'>
                    <input
                            className='w-48 m-3 p-1.5 text-center focus:bg-gray-50 hover:bg-gray-100 border border-gray-500 rounded-4xl'
                            type='password'
                            value={oldPassword}
                            name='oldPassword'
                            onChange={({ target }) => setOldPassword(target.value)}
                            placeholder='Old Password'
                    />    
                    <input
                            className='w-48 m-3 p-1.5 text-center focus:bg-gray-50 hover:bg-gray-100 border border-gray-500 rounded-4xl'
                            type='password'
                            value={newPassword}
                            name='newPassword'
                            onChange={({ target }) => setNewPassword(target.value)}
                            placeholder='New Password'
                    />    
                    <button className='w-48 m-3 p-1.5 bg-black text-white font-mono hover:bg-neutral-700 rounded-4xl' onClick={handlePasswordChange}>
                        Change Password
                    </button> 
                </div>
        </div>
    )
}

export default ChangePassword