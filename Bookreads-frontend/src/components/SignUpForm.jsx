import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import signupService from '../services/signup'

const SignUpForm = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const [email, setEmail] = useState('')
    const navigate = useNavigate()
    
    const handleSignUp = async (e) => {
        e.preventDefault()

        const credentials = {
            username,
            password,
            email
        }
        
        try {
            const user = await signupService.signup(credentials)
            if (user !== null) {
                setUsername('')
                setPassword('')
                setEmail('')
                navigate('/login')
            }
        } catch (error) {
            console.log(error)
        }
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
            <p>Already have an account? <Link to='/login'>Log in</Link></p>
        </div>
    )
}

export default SignUpForm