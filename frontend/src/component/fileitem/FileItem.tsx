import React from 'react'
import FileDTO from '../dto/FileDTO'
import URL from '../_config/config'

type FileItemType = {
	item: FileDTO
}

export default function FileItem({ item }: FileItemType) {
	const onDelete = () => {
		fetch(`${URL}/file/${item.id}`, {
			method: 'DELETE',
		})
			.then(res => {
				console.log('delete success')
				window.location.replace('/')
			})
			.catch(err => console.log(err))
	}

	return (
		<div>
			<li key={item.id}>
				<a href={`${URL}/file/download/${item.id}`}>
					{item.fileName}, {item.fileSize}, {item.id}, {item.owner}
				</a>
				<button onClick={onDelete}>delete</button>
			</li>
		</div>
	)
}
