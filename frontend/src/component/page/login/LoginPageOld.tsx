import React, { useState } from 'react'
import call from '../../utility/utility'

export default function LoginPage() {
	const [id, setId] = useState('')
	const [password, setPassword] = useState('')
	const handleLogin = () => {
		const fakeSecret = 'fakeSecretfakeSecretfakeSecretfakeSecretfakeSecret'
		console.log('로그인 완료')
		console.log('id', id)
		console.log('password', password)
		const body = { id, password }
		console.log(JSON.stringify(body))
		call('/login', 'POST', undefined, body, true).then(res => {
			window.location.href = '/main'
		})
	}
	return (
		<div>
			<div>
				ID : <input type="text" onChange={e => setId(e.target.value)} />
			</div>
			<div>
				Password : <input type="password" onChange={e => setPassword(e.target.value)} />
			</div>
			<button onClick={handleLogin}>Login</button>
			<button onClick={() => (window.location.href = '/register')}>Register</button>
		</div>
	)
}
