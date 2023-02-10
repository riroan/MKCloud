import { FormEvent, useState } from 'react'
import Avatar from '@mui/material/Avatar'
import Button from '@mui/material/Button'
import CssBaseline from '@mui/material/CssBaseline'
import TextField from '@mui/material/TextField'
import Link from '@mui/material/Link'
import Grid from '@mui/material/Grid'
import Box from '@mui/material/Box'
import LockOutlinedIcon from '@mui/icons-material/LockOutlined'
import Typography from '@mui/material/Typography'
import Container from '@mui/material/Container'
import { createTheme, ThemeProvider } from '@mui/material/styles'
import styles from './RegisterPage.module.scss'
import classnames from 'classnames/bind'
import call from '../../utility/utility'
import { StatusCodes } from 'http-status-codes'
import crypto from 'crypto-js'
const cx = classnames.bind(styles)

function Copyright(props: any) {
	return (
		<Typography variant="body2" color="text.secondary" align="center" {...props}>
			{'Copyright © '}
			<Link color="inherit" href="https://mui.com/">
				Your Website
			</Link>{' '}
			{new Date().getFullYear()}
			{'.'}
		</Typography>
	)
}

const theme = createTheme()

export default function SignUp() {
	const [idError, setIdError] = useState(false)
	const [passwordError, setPasswordError] = useState(false)
	const [passwordCheckError, setPasswordCheckError] = useState(false)
	const [idErrorMessage, setIdErrorMessage] = useState('')
	const [passwordErrorMessage, setPasswordErrorMessage] = useState('')
	const [passwordCheckErrorMessage, setPasswordCheckErrorMessage] = useState('')

	const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
		event.preventDefault()
		const data = new FormData(event.currentTarget)
		const id = data.get('id')
		const password = data.get('password')
		const passwordCheck = data.get('passwordCheck')
		let valid = true

		setIdError(false)
		setPasswordError(false)
		setPasswordCheckError(false)
		if (!id) {
			setIdError(true)
			setIdErrorMessage('아이디가 비어있습니다!')
			valid = false
		}
		if (!password) {
			setPasswordError(true)
			setPasswordErrorMessage('비밀번호가 비어있습니다!')
			valid = false
		} else if (password.toString().match(/^[A-Za-z0-9]{8,20}$/) === null) {
			setPasswordError(true)
			setPasswordErrorMessage('비밀번호는 영문 숫자를 조합해 8자리 이상이어야 합니다!')
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
			const pw = crypto.SHA512(password!.toString()).toString()
			const body = { id, password: pw }
			call('/register', 'POST', undefined, body, true).then(res => {
				if (res.status === StatusCodes.BAD_REQUEST) {
					setIdError(true)
					setIdErrorMessage('이미 존재하는 아이디입니다!')
					return
				}
				alert('회원가입 요청이 성공적으로 전송되었습니다. 관리자 승인 후 서비스를 이용할 수 있습니다.')
				window.location.href = '/login'
			})
		}
	}

	return (
		<ThemeProvider theme={theme}>
			<Container component="main" maxWidth="xs">
				<CssBaseline />
				<Box
					sx={{
						marginTop: 8,
						display: 'flex',
						flexDirection: 'column',
						alignItems: 'center',
					}}
				>
					<Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
						<LockOutlinedIcon />
					</Avatar>
					<Typography component="h1" variant="h5">
						회원가입
					</Typography>
					<Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
						<Grid container spacing={2}>
							<Grid item xs={12}>
								<TextField error={idError} required fullWidth id="email" label="아이디" name="id" />
								{idError && <span className={cx('error')}>{idErrorMessage}</span>}
							</Grid>
							<Grid item xs={12}>
								<TextField error={passwordError} required fullWidth name="password" label="비밀번호" type="password" id="password" />
								{passwordError && <span className={cx('error')}>{passwordErrorMessage}</span>}
							</Grid>
							<Grid item xs={12}>
								<TextField error={passwordCheckError} required fullWidth name="passwordCheck" label="비밀번호 확인" type="password" id="passwordCheck" />
								{passwordCheckError && <span className={cx('error')}>{passwordCheckErrorMessage}</span>}
							</Grid>
						</Grid>
						<Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
							회원가입
						</Button>
						<Grid container justifyContent="flex-end">
							<Grid item>
								<Link href="/login" variant="body2">
									이미 아이디가 있으십니까?
								</Link>
							</Grid>
						</Grid>
					</Box>
				</Box>
				<Copyright sx={{ mt: 5 }} />
			</Container>
		</ThemeProvider>
	)
}
