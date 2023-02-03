import React from 'react'
import { DataGrid, GridColDef } from '@mui/x-data-grid'

const columns: GridColDef[] = [
	{ field: 'id', headerName: 'ID', flex: 0.5, align: 'center', headerAlign: 'center' },
	{ field: 'fileName', headerName: '파일 명', flex: 2, align: 'center', headerAlign: 'center' },
	{
		field: 'fileSize',
		headerName: '파일 크기',
		flex: 0.5,
		align: 'center',
		headerAlign: 'center',
	},
	{
		field: 'owner',
		headerName: '소유자',
		flex: 1,
		align: 'center',
		headerAlign: 'center',
	},
	{
		field: 'uploadTime',
		headerName: '업로드 시각',
		flex: 1,
		type: 'date',
		align: 'center',
		headerAlign: 'center',
	},
]

const rows = [
	{ id: 1, fileName: 'file1', fileSize: '164kb', owner: 'user1', uploadTime: 'yesterday' },
	{ id: 1, fileName: 'file1', fileSize: '164kb', owner: 'user1', uploadTime: 'yesterday' },
	{ id: 1, fileName: 'file1', fileSize: '164kb', owner: 'user1', uploadTime: 'yesterday' },
	{ id: 1, fileName: 'file1', fileSize: '164kb', owner: 'user1', uploadTime: 'yesterday' },
	{ id: 1, fileName: 'file1', fileSize: '164kb', owner: 'user1', uploadTime: 'yesterday' },
	{ id: 1, fileName: 'file1', fileSize: '164kb', owner: 'user1', uploadTime: 'yesterday' },
	{ id: 1, fileName: 'file1', fileSize: '164kb', owner: 'user1', uploadTime: 'yesterday' },
	{ id: 1, fileName: 'file1', fileSize: '164kb', owner: 'user1', uploadTime: 'yesterday' },
	{ id: 1, fileName: 'file1', fileSize: '164kb', owner: 'user1', uploadTime: 'yesterday' },
	{ id: 1, fileName: 'file1', fileSize: '164kb', owner: 'user1', uploadTime: 'yesterday' },
	{ id: 1, fileName: 'file1', fileSize: '164kb', owner: 'user1', uploadTime: 'yesterday' },
]

export default function MainPage() {
	return (
		<div style={{ width: '80%', margin: 'auto' }}>
			<DataGrid rows={rows} columns={columns} pageSize={10} rowsPerPageOptions={[5]} autoHeight></DataGrid>
		</div>
	)
}
