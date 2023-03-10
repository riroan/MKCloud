import React, { useState } from 'react'
import styles from './TrashPage.module.scss'
import classnames from 'classnames/bind'
import LinearProgress, { linearProgressClasses } from '@mui/material/LinearProgress'
import { styled } from '@mui/material/styles'
import Typography from '@mui/material/Typography'
import Template from '../../template/Template'
import ItemTable from '../common/ItemTable'
import call, { convertFileSize } from '../../utility/utility'
import UserDTO from '../../dto/UserDTO'
import UploadModal from './UploadModal'
const cx = classnames.bind(styles)

const BorderLinearProgress = styled(LinearProgress)(({ theme }) => ({
	height: 10,
	borderRadius: 5,
	[`&.${linearProgressClasses.colorPrimary}`]: {
		backgroundColor: theme.palette.grey[theme.palette.mode === 'light' ? 200 : 800],
	},
	[`& .${linearProgressClasses.bar}`]: {
		borderRadius: 5,
		backgroundColor: theme.palette.mode === 'light' ? '#1a90ff' : '#308fe8',
	},
}))

export default function TrashPage() {
	const [capacity, setCapacity] = useState(0)
	const [used, setUsed] = useState(0)

	React.useEffect(() => {
		call('/id?isDeleted=true', 'GET')
			.then(res => res.json())
			.then((res: UserDTO) => {
				setCapacity(res.capacity)
				setUsed(res.used)
			})
	}, [])
	return (
		<Template title="Trash">
			<div className={cx('progress')}>
				<Typography className={cx('usage')} variant="body1" color="text.primary">
					사용량
				</Typography>
				<div className={cx('progressbar')}>
					<BorderLinearProgress variant="determinate" value={(used / capacity) * 100} />
				</div>
				<Typography className={cx('value')} variant="body1" color="text.primary">{`${convertFileSize(used)}/${convertFileSize(capacity)}`}</Typography>
			</div>

			<div className={cx('group')}>
				<div className={cx('desc')}>좌클릭으로 복구하고 우클릭으로 파일삭제</div>
			</div>
			<ItemTable isDeleted={true} />
		</Template>
	)
}
