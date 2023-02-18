import React, { useState, useEffect } from 'react'
import styles from './ItemTable.module.scss'
import classnames from 'classnames/bind'
import call, { convertFileSize, dateFormat } from '../../utility/utility'
import FileDTO from '../../dto/FileDTO'
import URL from '../../_config/config'
import FileItem from './FileItem'
import { useNavigate, Link } from 'react-router-dom'
const cx = classnames.bind(styles)

type ItemTableProps = {
	isDeleted: boolean
}

export default function ItemTable({ isDeleted }: ItemTableProps) {
	const movePage = useNavigate()
	const [data, setData] = useState<FileDTO[]>([])
	useEffect(() => {
		console.log(`/file/all?isDeleted=${isDeleted}`)
		call(`/file/all?isDeleted=${isDeleted}`, 'GET', undefined, undefined, false, movePage)
			.then(res => res.json())
			.then(res => setData(res))
	}, [])

	return (
		<div className={cx('container')}>
			<FileItem className={cx('item', 'title')} id={'ID'} fileName={'파일 명'} fileSize={'파일 크기'} owner={'소유자'} uploadTime={'업로드 시각'} />
			<div className={cx('box')}>
				{data.map((value, ix) => (
					<Link key={ix} to={!isDeleted ? `${URL}/file/download/${value.id}` : ''}>
						<FileItem
							onContextMenu={(e: React.MouseEvent<HTMLElement>) => {
								e.preventDefault()
								let url = `/file/${value.id}`
								if (isDeleted) {
									url = `/file/remove/${value.id}`
								}
								call(url, 'DELETE').then(res => {
									window.location.reload()
									data.splice(ix, 1)
									setData([...data])
								})
							}}
							onClick={
								isDeleted
									? (e: React.MouseEvent<HTMLElement>) => {
										call(`/file/revive/${value.id}`, 'POST').then(res => {
											window.location.reload()
											data.splice(ix, 1)
											setData([...data])
										})
									  }
									: () => {}
							}
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
