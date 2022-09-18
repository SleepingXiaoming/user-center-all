import { Settings as LayoutSettings } from '@ant-design/pro-components';

const Settings: LayoutSettings & {
  pwa?: boolean;
  logo?: string;
} = {
  navTheme: 'light',
  // 拂晓蓝
  primaryColor: '#1890ff',
  layout: 'mix',
  contentWidth: 'Fluid',
  fixedHeader: false,
  fixSiderbar: true,
  colorWeak: false,
  title: '管理系统',
  pwa: false,
  logo: 'https://himg.bdimg.com/sys/portrait/item/pp.1.710d4d60.WTM8p65W6-iIRGtzGsvJMg.jpg?tt=1662117985721',
  iconfontUrl: '',
};

export default Settings;
