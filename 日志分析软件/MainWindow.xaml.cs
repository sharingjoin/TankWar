using System;
using System.Windows;
using Microsoft.Win32;

namespace RegexLesson02
{
    /// <summary>
    /// MainWindow.xaml 的交互逻辑
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
            _Model = new MainModel();
            this.DataContext = _Model;
        }
        private MainModel _Model;

        private void OnLoad_Executed(object sender, System.Windows.Input.ExecutedRoutedEventArgs e)
        {
            OpenFileDialog aDlg = new OpenFileDialog();
            aDlg.Filter = @"日誌文件|*.log";//@忽略轉義符.$呢？
            if (aDlg.ShowDialog() != true) return;
            try
            {
                _Model.Load(aDlg.FileName);//調用Load()方法，該方法已經在MainModel.cs中實現。
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void OnLoad_CanExecute(object sender, System.Windows.Input.CanExecuteRoutedEventArgs e)
        {
            e.CanExecute = true;
        }

        private void OnStartFilter_Executed(object sender, System.Windows.Input.ExecutedRoutedEventArgs e)
        {
           // MessageBox.Show(_Model.Now_CheckStrings);

            try
            {
                _Model.DoFilter();//調用DoFilter()方法，該方法已經在MainModel.cs中實現。
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void OnStartFilter_CanExecute(object sender, System.Windows.Input.CanExecuteRoutedEventArgs e)
        {
            e.CanExecute = true;
        }

        private void OnLoadRegularFilter_Executed(object sender, System.Windows.Input.ExecutedRoutedEventArgs e)
        {
            //_Model.add_strign();
            //OpenFileDialog bDialog = new OpenFileDialog();
            //bDialog.Filter = "正则表达式文件|*.txt;*docx";
            //if (bDialog.ShowDialog() != true) return;
            //try
            //{
            //    _Model.LoadRegularFilter(bDialog.FileName);
            //}
            //catch (Exception ex)
            //{
            //    MessageBox.Show(ex.Message);
            //}
            _Model.Load_Sql();
        }

        private void OnLoadRegularFilter_CanExecute(object sender, System.Windows.Input.CanExecuteRoutedEventArgs e)
        {
            e.CanExecute = true;
        }

        private void OnSave_To_Data_Executed(object sender, System.Windows.Input.ExecutedRoutedEventArgs e)
        {
            _Model.Create_DataBase();
            _Model.Save_DataBase();
        }

        private void OnSave_To_Data_CanExecute(object sender, System.Windows.Input.CanExecuteRoutedEventArgs e)
        {
            e.CanExecute = true;
        }
    }
}
