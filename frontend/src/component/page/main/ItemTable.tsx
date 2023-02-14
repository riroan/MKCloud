import React, { useState, useEffect } from 'react'
import styles from './ItemTable.module.scss'
import classnames from 'classnames/bind'
import call, { convertFileSize, dateFormat } from '../../utility/utility'
import FileDTO from '../../dto/FileDTO'
import URL from '../../_config/config'
import FileItem from './FileItem'
import { useNavigate, Link } from 'react-router-dom'
const cx = classnames.bind(styles)

export default function ItemTable() {
	const movePage = useNavigate()
	const [data, setData] = useState<FileDTO[]>([])
	useEffect(() => {
		call('/file/all', 'GET', undefined, undefined, false, movePage)
			.then(res => res.json())
			.then(res => setData(res))
	}, [])

	return (
		<div className={cx('container')}>
			<FileItem className={cx('item', 'title')} id={'ID'} fileName={'파일 명'} fileSize={'파일 크기'} owner={'소유자'} uploadTime={'업로드 시각'} />
			<div className={cx('box')}>
				{data.map((value, ix) => (
					<Link key={ix} to={`${URL}/file/download/${value.id}`}>
						<FileItem
							onContextMenu={(e: React.MouseEvent<HTMLElement>) => {
								e.preventDefault()
								call(`/file/${value.id}`, 'DELETE').then(res => {
									window.location.reload()
									data.splice(ix, 1)
									setData([...data])
								})
							}}
							className={cx('item')}
							id={ix + 1}
							fileName={value.fileName}
							fileSize={convertFileSize(value.fileSize)}
							owner={value.owner}
							uploadTime={dateFormat(new Date(value.uploadTime))}
						/>
					</Link>
				))}
				<div className={cx('scroll')}></div>
			</div>
		</div>
	)
}
