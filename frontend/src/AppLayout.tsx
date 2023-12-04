import { Layout, LayoutProps } from "react-admin";
import { MainMenu } from "./app/menu/MainMenu";

export const AppLayout = (props: LayoutProps) => <Layout {...props} menu={MainMenu} />;
