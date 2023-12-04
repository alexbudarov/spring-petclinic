import { Menu, MenuProps } from "react-admin"
import PeopleIcon from '@mui/icons-material/People';

export const MainMenu = () => (
    <Menu>
        <Menu.ResourceItems />
        <Menu.Item to="/visit/request" primaryText="Request Visit" leftIcon={<PeopleIcon />} />
    </Menu>
)