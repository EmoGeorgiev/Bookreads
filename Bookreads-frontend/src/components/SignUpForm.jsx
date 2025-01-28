import { useState } from 'react'

const SignUpForm = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const [email, setEmail] = useState('')
    
    const handleSignUp = (e) => {
        e.preventDefault()
    }

    return (
        <div>
            <h1>Bookreads</h1>
            <h1>Create Account</h1>
            <form onSubmit={handleSignUp}>
                <div>
                    <input 
                        type='text'
                        value={username}
                        name='username'
                        onChange={({ target }) => setUsername(target.value)}
                        placeholder='Username'
                    />
                </div>
                <div>
                    <input 
                        type='password'
                        value={password}
                        name='password'
                        onChange={({ target }) => setPassword(target.value)}
                        placeholder='Password'
                    />
                </div>
                <div>
                    <input 
                        type='email'
                        value={email}
                        name='email'
                        onChange={({ target }) => setEmail(target.value)}
                        placeholder='Email'
                    />
                </div>
                <button>Create account</button>
            </form>
            <p>Already have an account? Log in</p>
        </div>
    )
}

export default SignUpForm