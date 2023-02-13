import React, { useState, useEffect } from 'react'
import { useDropzone } from 'react-dropzone'
import styles from './UploadModal.module.scss'
import classnames from 'classnames/bind'
import { Button, Stack } from '@mui/material'
import { convertFileSize } from '../../utility/utility'
import URL from '../../_config/config'
import { useNavigate } from 'react-router-dom'
const cx = classnames.bind(styles)

type UploadModalProps = {
	setOpen: (open: boolean) => void
	used: number
	capacity: number
}

export default function UploadModal({ setOpen, used, capacity }: UploadModalProps) {
	const movePage = useNavigate()
	const { acceptedFiles, getRootProps, getInputProps } = useDropzone()
	const [files, setFiles] = useState<File[]>([])
	const [error, setError] = useState(false)
	const [errorMessage, setErrorMessage] = useState('')
	const selectedFiles = files.map((file, ix) => (
		<li key={ix} className={cx('item')}>
			{file.name} <span className={cx('size')}>({convertFileSize(file.size)})</span>{' '}
			<Button
				className={cx('delete')}
				variant="contained"
				onClick={() => {
					acceptedFiles.splice(ix, 1)
					setFiles([...acceptedFiles])
				}}
			>
				삭제
			</Button>
		</li>
	))
	const handleSubmit = () => {
		let sum = 0
		let valid = true

		setError(false)
		if (acceptedFiles.length === 0) {
			setError(true)
			setErrorMessage('파일을 선택해주세요!')
			return
		}
		for (var file of acceptedFiles) {
			sum += file.size
		}

		if (sum + used > capacity) {
			setError(true)
			setErrorMessage('저장 한도를 초과했습니다.')
			valid = false
		}
		if (valid) {
			const formData = new FormData()
			acceptedFiles.map(file => formData.append('multipartFiles', file))
			for (let i of formData.values()) console.log(i)
			fetch(`${URL}/file/save`, {
				method: 'POST',
				credentials: 'include',
				body: formData,
			}).then(res => {
				alert('파일 전송이 완료되었습니다.')
				setOpen(false)
				movePage('/')
			})
		}
	}

	useEffect(() => {
		setFiles([...acceptedFiles])
		setError(false)
	}, [acceptedFiles])

	const closeModal = () => {
		setOpen(false)
	}

	return (
		<div className={cx('container')}>
			<div className={cx('layer')} onClick={closeModal}></div>
			<div className={cx('modal')}>
				<div className={cx('title')}>업로드</div>

				{acceptedFiles.length > 0 ? (
					<div className={cx('dropzone')}>{selectedFiles}</div>
				) : (
					<div {...getRootProps({ className: cx('dropzone', 'input') })}>
						<input {...getInputProps()} />
						<div className={cx('text')}>업로드할 파일을 끌어오거나 이 곳을 클릭하세요</div>
					</div>
				)}

				<div className={cx('error')}>{error ? errorMessage : ' '}</div>
				<div className={cx('group')}>
					<Stack direction="row" spacing={2}>
						<Button variant="outlined" className={cx('button')} sx={{ fontSize: 17 }} onClick={handleSubmit}>
							업로드
						</Button>
						<Button variant="outlined" className={cx('button')} sx={{ fontSize: 17 }} onClick={closeModal} color="error">
							취소
						</Button>
						<Button
							variant="outlined"
							className={cx('button')}
							sx={{ fontSize: 17 }}
							onClick={() => {
								acceptedFiles.length = 0
								setFiles([])
								setError(false)
							}}
							color="error"
						>
							초기화
						</Button>
					</Stack>
				</div>
			</div>
		</div>
	)
}
