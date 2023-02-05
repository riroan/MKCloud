import React from 'react'
import styles from './FileItem.module.scss'
import classnames from 'classnames/bind'
const cx = classnames.bind(styles)

type FileItemProps = {
	id: number | string
	fileName: string
	fileSize: number | string
	owner: string
	uploadTime: string
	className?: string
	onClick?: (e: React.MouseEvent<HTMLElement>) => void
}

export default function FileItem({ id, fileName, fileSize, owner, uploadTime, className, onClick }: FileItemProps) {
	return (
		<div onClick={onClick} className={cx('container', className)}>
			<div className={cx('id')}>{id}</div>
			<div className={cx('fileName')}>{fileName}</div>
			<div className={cx('fileSize')}>{fileSize}</div>
			<div className={cx('owner')}>{owner}</div>
			<div className={cx('uploadTime')}>{uploadTime}</div>
		</div>
	)
}
