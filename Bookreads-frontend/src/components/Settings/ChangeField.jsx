import { useState } from 'react'

const ChangeField = ({ fieldName, fieldValue, fieldChange }) => {
    const [field, setField] = useState('')
    
    const handleFieldChange = e => {
        e.preventDefault()
        fieldChange(field)
        setField('')
    }

    return (
        <div>
            <h2>Username change</h2>
            <form onSubmit={handleFieldChange}>
                <div>
                    <p>Current {fieldName} : {fieldValue}</p>
                    <input
                        type='text'
                        value={field}
                        name={fieldName}
                        onChange={({ target }) => setField(target.value)}
                        placeholder={fieldName}
                    />
                </div>
                <div>
                    <button>
                        Change {fieldName}
                    </button>
                </div>
            </form>
        </div>
        )
}

export default ChangeField