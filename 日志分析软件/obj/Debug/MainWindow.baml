   M S B A M L   `   `   `  ����H  DRegexLesson02, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null-clr-namespace:RegexLesson02RegexLesson02  5   	       RegexLesson02.MainWindow   5   	   \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   -localclr-namespace:RegexLesson02     5   	     � Title$  日志查看器��5   	   $	��600��6   $	��800��6$    � 	Resources 5	         Load  StartFilter  LoadRegularFilter  Save_To_Data��%t   &	        &	      &	 &     &	 9     �� 5
   
   6   �� 5   
   6   �� 5   
   6   �� 5   
   6   5       J� CommandBindings 5      )  #System.Windows.Input.CommandBinding  5   
   -   5           Command# [  5      6T     5   
   -   5        # [ 5      6b     5   
   -   5        # [ 5      6o     5   
   -   5        # [ 5      6e   .��5      ]� .+�5   
   c� $	��Top=�6   .@�5      �� $��打开文件��6   #��[  6$   �� 5      $	��100��6/   ��6   �� *	Pattern+6/   �� 5      $��开始筛选��6   #��[ 6$   �� 5       � Minimum$ 9Q�6    � Maximum$	 200Q�6!   $	��200��6d    � Value 6/   ��  �� ElementName$ lstTexts�� �� Path$ FontSize�6d   �� 5      $��RegularTextFile��6   #��[ 6/   �� 5      ��6   �� *CheckStrings+	 �� SelectedItem	 6<   �� *	Pattern+�� 5      $��存入数据库��6   #��[ 6%   5      A� 5"   
   
 A� Columns$
 1��6   .+�5#      �� $��
FilterText��6   .��5$      �� -   5         ��Name$ lstTexts��5$   @   ��6   �� *FilteredTexts+6@   5%      5'      5)      5*      