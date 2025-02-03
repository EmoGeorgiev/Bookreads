import { useState } from 'react'

const ChangeEmail = ({ email, emailChange }) => {
    const [newEmail, setNewEmail] = useState('')

    const handleEmailChange = e => {
        e.preventDefault()

        try {
            emailChange(newEmail)
            setNewEmail('')
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <div>
            <h2>Email change</h2>
            <form onSubmit={handleEmailChange}>
                <div>
                    <p>Current username : {email}</p>
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
    )
}

export default ChangeEmail