import React, { FormEvent, useState, useEffect } from 'react'
import Button from '@mui/material/Button'
import TextField from '@mui/material/TextField'
import Grid from '@mui/material/Grid'
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import Container from '@mui/material/Container'
import styles from './UserPage.module.scss'
import classnames from 'classnames/bind'
import call from '../../utility/utility'
import Template from '../../template/Template'
import UserDTO from '../../dto/UserDTO';
const cx = classnames.bind(styles)

export default function UserPage() {
	const [id, setId] = useState('')
	const [passwordError, setPasswordError] = useState(false)
	const [passwordCheckError, setPasswordCheckError] = useState(false)
	const [passwordErrorMessage, setPasswordErrorMessage] = useState('')
	const [passwordCheckErrorMessage, setPasswordCheckErrorMessage] = useState('')
	const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
		event.preventDefault()
		const data = new FormData(event.currentTarget)
		const password = data.get('password')
		const passwordCheck = data.get('passwordCheck')
		let valid = true

		setPasswordError(false)
		setPasswordCheckError(false)
		if (!password) {
			setPasswordError(true)
			setPasswordErrorMessage('비밀번호가 비어있습니다!')
			valid = false
		}
		if (!passwordCheck) {
			setPasswordCheckError(true)
			setPasswordCheckErrorMessage('비밀번호 확인이 비어있습니다!')
			valid = false
		} else if (password !== passwordCheck) {
			setPasswordCheckError(true)
			setPasswordCheckErrorMessage('비밀번호가 일치하지 않습니다!')
			valid = false
		}
		if (valid) {
			const body = { password }
			call('/password', 'POST', undefined, body, true).then(res => {
				window.location.href = '/user'
				alert("비밀번호가 변경되었습니다.")
			})
		}
	}

	useEffect(() => {
		call('/id', 'GET')
			.then(res => res.json())
			.then((res:UserDTO) => {
				setId(res.id)
			})
	}, [])
	return (
		<Template title="My Page">
			<div className={cx('title')}>
				<Typography component="h1" variant="h4">
					내 정보
				</Typography>
			</div>

			<Container component="main" maxWidth="xs">
				<Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 3 }}>
					<Grid container spacing={2}>
						<Grid item xs={12}>
							<TextField disabled value={id} fullWidth label="아이디" id="id" name="id" />
						</Grid>
						<Grid item xs={12}>
							<TextField error={passwordError} required fullWidth name="password" label="새 비밀번호" type="password" id="password" />
							{passwordError && <span className={cx('error')}>{passwordErrorMessage}</span>}
						</Grid>
						<Grid item xs={12}>
							<TextField error={passwordCheckError} required fullWidth name="passwordCheck" label="새 비밀번호 확인" type="password" id="passwordCheck" />
							{passwordCheckError && <span className={cx('error')}>{passwordCheckErrorMessage}</span>}
						</Grid>
					</Grid>
					<Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
						비밀번호 변경
					</Button>
				</Box>
			</Container>
		</Template>
	)
}
