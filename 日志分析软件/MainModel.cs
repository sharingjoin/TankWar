using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data.Linq;
using System.IO;
using System.Text;
using System.Text.RegularExpressions;
using System.Windows;

namespace RegexLesson02
{   //MainWindow.xaml写要用到的方法，MainModel.cs實現MainWindow.xaml所需要的方法，MainWindow.xaml.cs調用MainWindow.xaml中說明的方法，
    //Executed,CanExecute后面的方法,比如 Executed="OnLoad_Executed" CanExecute="OnLoad_CanExecute"
    class MainModel : INotifyPropertyChanged
    {
        public void Load(string aFileName)
        {
            string[] aLines = File.ReadAllLines(aFileName);
            SourceTexts = aLines;
        }
        public void LoadRegularFilter(string bFilename)
        {
            string[] bLines = File.ReadAllLines(bFilename);
            // _CheckStrings = new string[10];
            int i = 0;
            foreach (string bline in bLines)
            {
                _CheckStrings[i++] = bline;
            }
        }

        public void DoFilter()
        {
            if (string.IsNullOrEmpty(Pattern))
            {
                FilteredTexts = new List<string>(SourceTexts);
            }
            else
            {
                Regex aRegex = new Regex(Pattern);
                List<string> aLines = new List<string>();
                foreach (string aLine in SourceTexts)
                {
                    if (aRegex.IsMatch(aLine))
                        aLines.Add(aLine);
                }
                FilteredTexts = aLines;
            }
        }

        public string[] SourceTexts
        {
            get { return _SourceTexts; }
            set
            {
                if (_SourceTexts == value) return;
                _SourceTexts = value;
                DoFilter();
                OnPropertyChanged(nameof(SourceTexts));
            }
        }
        private string[] _SourceTexts;

        public List<string> FilteredTexts { get { return _FilterdTexts; } private set { if (_FilterdTexts == value) return; _FilterdTexts = value; OnPropertyChanged(nameof(FilteredTexts)); } }
        private List<string> _FilterdTexts;

        public string Pattern { get { return _Pattern; } set { if (_Pattern == value) return; _Pattern = value; OnPropertyChanged(nameof(Pattern)); } }
        private string _Pattern;
        //下拉框
        public string[] CheckStrings { get { return _CheckStrings; } set { if (_CheckStrings == value) return; _CheckStrings = value; OnPropertyChanged(nameof(CheckStrings)); } }
        private static string[] _CheckStrings = new string[10];

        private void OnPropertyChanged(string aPropertyName)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(aPropertyName));
        }
        public event PropertyChangedEventHandler PropertyChanged;

        const string ConnectionString = @"Data Source=(localdb)\Projects;Initial Catalog=DataCeShi;Integrated Security=True; Connect Timeout=30;Encrypt=False;TrustServerCertificate=False;";
        DataDataContext DataContext=new DataDataContext(ConnectionString);
        public void Create_DataBase()
        {
            try
            {
                // 连接数据库引擎
                using (DataDataContext aDataContext1 = new DataDataContext(ConnectionString))
                {
                    if (!aDataContext1.DatabaseExists())
                    {
                        aDataContext1.CreateDatabase();
                        MessageBox.Show("数据库已经创建！");
                    }
                    else
                    {
                       //MessageBox.Show("数据库已经存在！");
                    }
                }
                DataDataContext aDataContext = new DataDataContext(ConnectionString);
                DataContext = new DataDataContext(ConnectionString);
                aDataContext.SubmitChanges();
            }
            catch (Exception msg)
            {
                MessageBox.Show(msg.Message);
            }

        }

        private static int index = 0;
        public void Load_Sql()
        {
            DataDataContext aDataContext = new DataDataContext(ConnectionString);
            Contacts = aDataContext.DataCeShi;
            foreach (object i in Contacts)
            {
                DataCeShi d = (DataCeShi)i;
                CheckStrings[index++] = d.Regular;
            }
        }

        public void Save_DataBase()
        {
            if (Pattern != null)
            {
                Create_DataBase();
                DataCeShi aNewContact = new DataCeShi {Regular=Pattern };
                DataDataContext aDataContext = new DataDataContext(ConnectionString);
                aDataContext.DataCeShi.InsertOnSubmit(aNewContact);
                aDataContext.SubmitChanges();
                Contacts = aDataContext.DataCeShi;
                foreach (object i in Contacts)
                {
                    DataCeShi d = (DataCeShi)i;
                    CheckStrings[index++] = d.Regular;
                }
            }
            Update();
        }
        public void Update()
        {
            DataDataContext aDataContext = new DataDataContext(ConnectionString);
            aDataContext.SubmitChanges();
        }

        private Table<DataCeShi> _contacts;

        public Table<DataCeShi> Contacts
        {
            get
            {
                DataDataContext aDataContext = new DataDataContext(ConnectionString);
                return aDataContext.DataCeShi;
            }
            set
            {
                if (_contacts == value)
                    return;
                _contacts = DataContext.DataCeShi;
                OnPropertyChanged(nameof(Contacts));
            }
        }

    }
}
