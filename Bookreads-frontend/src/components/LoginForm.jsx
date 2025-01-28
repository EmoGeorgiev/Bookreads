import { useState } from 'react'

const LoginForm = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

    const handleLogin = (e) => {
        e.preventDefault()
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
            <p>New to Bookreads? Sign up</p>
        </div>
    )
}

export default LoginForm    