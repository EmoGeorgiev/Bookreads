import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { useAuth } from './AuthContext'
import loginService from '../services/login'
 
const LoginForm = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const navigate = useNavigate()
    const { login } = useAuth()

    const handleLogin = async (e) => {
        e.preventDefault()

        try {
            const data = await loginService.login({
                username,
                password
            })
            login(data)
            setUsername('')
            setPassword('')
            navigate('/')
        } catch (error) {
            console.log(error)
        }
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
            <p>New to Bookreads? <Link to='/signup'>Sign up</Link></p>
        </div>
    )
}

export default LoginForm    