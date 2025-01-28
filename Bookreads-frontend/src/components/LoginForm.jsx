import { useState } from 'react'

const LoginForm = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

    const handleLogin = (e) => {
        e.preventDefault()
    }

    const handleSignUp = () => {

    }

    return (
        <div>
            <h1>Bookreads</h1>
            <h1>Log in</h1>

            <form onSubmit={handleLogin}>
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
                <button>Log in</button>
            </form>
            <p>New to Bookreads?</p>
            <button onClick={handleSignUp}>Sign up</button>
        </div>
    )
}

export default LoginForm    