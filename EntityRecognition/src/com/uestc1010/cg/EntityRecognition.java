package com.uestc1010.cg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Vector;

/*
 * date 2014.4
 * author uestc1010
 * ˵�����Զ�����ı�����ƥ���ע
 * 
 * */
public class EntityRecognition {

	//ʵ��ʶ�𣬾��Ӽ���ʶ��
	public void entitiyRecognition(Vector<String> sentence) throws IOException{
		//���䴦��(���� ������������Ϊһ��)
		Iterator iter = sentence.iterator();
		while(iter.hasNext()){
			String sen = (String) iter.next();
			//System.out.println(sen);
			String doSen = match(sen);
//			doSen = mark(doSen);
//			System.out.println(doSen);
			doSen = markTwo(doSen);
		}

		
	}
	//�ַ�������
	public void entitiyRecognition(String string) throws IOException{
		Vector<String> words = sentence(string);
		Iterator iter = words.iterator();
		while(iter.hasNext()){
			String sen = (String) iter.next();
			//System.out.println(sen);
			String doSen = match(sen);
//			doSen = mark(doSen);
//			System.out.println(doSen);
			doSen = markTwo(doSen);
		}
		
	}
	//�ļ��ı�ʶ��
	public void entitiyRecognition(File file) throws IOException{
		// to be continue~~~
	}
	//�ļ�����
	String readFile(File file) throws IOException{
		BufferedReader read = new BufferedReader(new FileReader(file));
		String temp = null;
		String sb = "";
		while((temp = read.readLine()) != null){
			sb += temp;
		}
		return sb;
	}
	//�Ͼ�
	Vector<String> sentence(String str){
		Vector<String> words = new Vector<String>();
		String buf ="";
		boolean flag = false;
		int len = str.length();
		for(int i = 0 ; i < len; i++){
			char word = str.charAt(i);
			if((word == '��')||(word == '��')||(word == '��')||(word == '��')||(word == '��')){
				buf += word ;
				flag = true;
		    }else
				 buf += word;
			 //�Ѿ���ȫ������hashmap ,��ʼȨֵȫ��Ϊ1
			 if(flag == true){
				 words.add(buf);
				 flag = false;
				 buf ="";
			 }
		}
		//System.out.println(words);
		return words;
	}
	//�����Ͽ����ƥ��
	String match(String s) throws IOException{
		//�ֱ������� ���� ��֯��������ƥ�� ��������һ��������Ϊ����
		File file = new File("���ϴ�ȫ.txt");
		BufferedReader read = new BufferedReader(new FileReader(file));
		String temp = null;
		String sb = s;
		while((temp = read.readLine()) != null){  //�е�С���⣬Ϊëÿ��IF�ж϶�������true����
			//�������ĳ�����ϣ��ͽ��б�ע
			if(sb.contains(temp)){
				sb = mark(sb,temp);
//				System.out.println(sb);
			}
		}
		return sb;
	}
	
	//���б�ע,���������Ͽ���ƥ��Ĺؼ��ֽ��б�ע
	String mark(String s,String key){
		//��ƥ����ַ���ǰ������ض��ı�ע,��s�е�key�ַ���ǰ���ע
		//ע�⣺һ�仰���ж��key
		StringBuffer buf = new StringBuffer(s);
		int count = 0; //��¼��ӱ�ע���ַ���buf�ĳ���
		int length = s.length();
		for(int i = 0; i < length ; i++){
			String buff="";
			buff += s.charAt(i);
//			System.out.println(buff);
			if(!buff.equals(key)){
				//System.out.println("no");
				//break;
			}
			else{
				//System.out.println("YES");
				buf.insert(i+count, "��");
				count += 2;
				buf.insert(i+count, "��");
				
			}
		}
//		System.out.println(buf.toString());
		return buf.toString();
	}
	
	/*���α�ע���������Ļ��������Ĺؼ��ֽ��б�ע��ȷ��ʵ��ı߽�
	 * �жϹؼ���ǰ����ؼ����Ƿ�����ɴ���
	 * �жϹؼ��ʺ�����ؼ����ܷ���ɴ���
	 * �жϹؼ��ʺ�����ؼ����ܷ��������
	 * �������жϣ�����ؼ��֣�˵������Ի���ǵȵȣ�
	 */
	String markTwo(String s) throws IOException{
		//��ȷ���߽����ַ���ǰ������ض��ı�ע
		Vector<String> name = new Vector<String>();
		String buf = s;
		
		if(!buf.contains("��")){
//			System.out.println("no");
		}else{
//			System.out.println("YES");
			int length = buf.length();
			for(int i = 0 ;i < length ;i ++){
				String buf1 = "";
				String buf2 = "";
				String word="";
				String word1="";
				 word += buf.charAt(i);
				if(word.equals("��")){
					if(((i+2) < length) && (word1 += buf.charAt(i+2)).equals("��")){
						buf1 += buf.charAt(i+1);  //��õ�һ�α�ǵĹؼ���= 
//						System.out.println(buf1);
						name = nameProbability(buf1); //������ϵ�ȫ�����֣��Ժ��ø�������ã�
						if((i+3) < length){
							buf1 += buf.charAt(i+3);
							buf2 = buf1;
						}
						if((i+4) < length)
							buf1 +=buf.charAt(i+4);
						if(name.contains(buf1)){
							outPut(buf1);
						}else if(name.contains(buf2)){
							outPut(buf2);
						}else;
						
					}
				}
			}
		}
		return null;
	}
	//�������ϣ��ҵ���Ӧ�������ı����ҵ����ַ�������
	Vector<String> nameProbability(String str) throws IOException{
		Vector<String>word = new Vector<String>();
		String buf ="";
		File file = new File(".\\Data");
		String name[];
		name = file.list();
		for(int i = 0 ; i < name.length; i++ ){
//			System.out.println(name[i]);
			if(name[i].contains(str)){
				buf = name[i];
			}
			if(buf != ""){
				String filename = ".\\Data"+"\\"+buf;
				File file1 = new File(filename);
				InputStreamReader isr =new InputStreamReader(new FileInputStream(file1),"UTF-8");
				BufferedReader read = new BufferedReader(isr);
				String temp =null;
		
				while((temp = read.readLine()) != null){
//					System.out.println(temp);
					word.add(temp);
				}
//				System.out.println(word);
				
			}
			buf ="";
		}
		
		return word;
	}
	//���ʶ���ʵ��
	void outPut(String s){
		System.out.print(s+"  ");
	}
	
	//�����ֵĸ���,�������ĸ����Լ���֯�������ĸ���
	double Probability(String name){
		//ͳ���������ֹ��� ���ָ���
		return 0;
	}
	
	//���й�����ĸ���
	double wordsProbability(String word){
		//ͳ��������������ĸ���
		return 0;
	}
	
	
	public static void main(String []args) throws IOException{

		String test = "��ΰ���¡����ѵ���������棬ʩչ����С�壬�ſ�������������ӽ���������ǹ�����ϯ��";
		EntityRecognition xx = new EntityRecognition();
		xx.entitiyRecognition(xx.sentence(test));
		
		
	}
}

