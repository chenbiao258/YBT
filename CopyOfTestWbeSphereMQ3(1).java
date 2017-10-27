package com.sinosoft.service.business.ui.CustomerID.utils;

import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

public class CopyOfTestWbeSphereMQ3 {
		
	public String  sendInfoToCRPT(Object XMLDOC){
		

		String content = new String();
		StringBuffer sbContent = new StringBuffer();
		sbContent.append("ISM Fixed V02.00YNHSBC_YBT                          ");
		sbContent.append("                                                    ");
		sbContent.append("                                                                                                                                                                                                                                                                                                 ");
		sbContent.append("Z2017-10-09T20:55:28.000069 +08:00                                                                                                                                                                                                                                                                              ");
		sbContent.append("0000007890000000000000001290000004530000002070010101OH_SERVICE_HEADER               00860100000   INTBCNHSBC                                                                  0101000199000199HBCN_HUB_CreditCard                                             ENQCUSSUM                                                       01.0001.00                                                                                                                                                                                                                                                                                                           01000199");
		StringBuffer headContent = new StringBuffer();
		headContent.append("1234554321");//银行代码
		headContent.append("3344    ");//地区代码
		headContent.append("20                  ");//网点代码
		headContent.append("2646            ");//交易流水号
		headContent.append("15");//交易代码
		headContent.append("GROUP_MEMBER: HSBC  ");//保险公司代码
		headContent.append("10052017082901      ");//投保单号
		headContent.append("10052017082901      ");//保单号
		content = sbContent.toString() + headContent.toString();
		
		String infoRec="";
		String QueueManagerString="DCNL001YBT";//发送队列管理器
		String qName ="CN_YBT.CN_YBT.CN_HUB.YBT_RQST.D1";//发送消息队列
		String replyToQueue ="CN_YBT.CN_HUB.CN_YBT.YBT_RSP.D1";//接收回复队列
		String replyToQueueManager ="DCNL001YBT";//接收队列管理器
		
		MQEnvironment.CCSID=935;
		MQEnvironment.port=15300;
		MQEnvironment.channel="CN.YBT.TMP";//发送通道
		MQEnvironment.hostname = "133.10.217.5";
		MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES_CLIENT);
		MQQueueManager manger=null; // 队列管理器名称
		MQQueue queueRec=null;// 发送消息通道
		MQQueue queue =null;// 接收消息通道
		try {
			manger= new MQQueueManager(QueueManagerString);
			System.out.println("连接队列管理器");
			int openOptionsArg =MQC.MQOO_OUTPUT | MQC.MQOO_FAIL_IF_QUIESCING ;
			queue= manger.accessQueue(qName, openOptionsArg);//建立通道的连接
			System.out.println("消息通道建立成功");
			MQMessage message= new MQMessage();//要写入的消息
			message.characterSet=935;
			message.encoding=935;
			message.messageFlags= MQC.MQMT_REQUEST;
			message.format=MQC.MQFMT_STRING;
			message.replyToQueueManagerName=replyToQueueManager;
			message.replyToQueueName=replyToQueue;
			
			MQPutMessageOptions  putOp = new MQPutMessageOptions();
			putOp.options= putOp.options+MQC.MQPMO_NEW_CORREL_ID;
			putOp.options= putOp.options+MQC.MQPMO_SYNCPOINT;
			message.writeObject(content);// 将消息放入队列
			System.out.println("成功放入消息");
			queue.put(message, putOp);
			/*提交事务*/
			manger.commit();
			
			System.out.println("请求消息发送成功 "+content+"\n correlationId: "+message.messageId);
			/*关闭请求队列*/
			//queue.close();
			
			System.out.println("准备接收回复........");
			
			/**
			 * 接收回复
			 */
			
			/*以下选项可适合远程队列与本地队列*/    
            //int openOptions = MQC.MQOO_INQUIRE | MQC.MQOO_OUTPUT; //发送时使用  
            //int qOptioin = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT; 接收时使用  
			
			//int openOptions = MQC.MQOO_INPUT_SHARED | MQC.MQOO_FAIL_IF_QUIESCING;
			
			int openOptions = MQC.MQOO_OUTPUT | MQC.MQOO_INPUT_SHARED;
			
			System.out.println("111111111");
			MQMessage messageRec= new MQMessage();//要读的队列的消息
//			messageRec.correlationId = message.messageId;
//			messageRec.characterSet=1381;
//			messageRec.encoding=1381;
//			messageRec.format=MQC.MQFMT_STRING;
			MQGetMessageOptions getOp = new MQGetMessageOptions();
			System.out.println("222222222");
			getOp.options = getOp.options +MQC.MQGMO_SYNCPOINT;
			getOp.options = getOp.options +MQC.MQGMO_WAIT;
			getOp.matchOptions = MQC.MQMO_MATCH_CORREL_ID;
			getOp.waitInterval= 30000;
			queueRec = manger.accessQueue(replyToQueue, openOptions);
			System.out.println("333333333");
			queueRec.get(messageRec, getOp);
			
			//2037的错误
			
			
			
			//infoRec =  messageRec.readStringOfByteLength(messageRec.getMessageLength());
			System.out.println("成功接收回复的信息："+messageRec); 
			
			manger.commit();
			
		} catch (MQException e) {
			e.printStackTrace();
			infoRec=e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			infoRec=e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			infoRec=e.getMessage();
		}finally{
			try {
				System.out.println("开始关闭MQ连接");
				queue.close();
				queueRec.close();
				manger.disconnect();
				System.out.println("Q连接关闭成功");
			} catch (MQException e) {
				e.printStackTrace();
			}
		}
		return infoRec;
	
		
	}
	
	public static void main(String[] args) {
		Element TranData = new Element("TranData");
        // 创建元素 及 设置为子元素  报文头
        Element Head = new Element("Head");
        
        Element TranDate = new Element("TranDate");//交易日期
        TranDate.setText("111");
        Element TranTime = new Element("TranTime");//交易时间
        TranTime.setText("222");
        Element TranCom = new Element("TranCom");//银行代码
        TranCom.setText("YH");
        Element NodeNo = new Element("NodeNo");//网点代码
        NodeNo.setText("");
        Element TellerNo = new Element("TellerNo");//柜员代码
        TellerNo.setText("");
        Element FuncFlag = new Element("FuncFlag");//交易类型
        FuncFlag.setText("cifquery");
        Element ChannelNo = new Element("ChannelNo");//渠道代码
        ChannelNo.setText("");
        Element TranNo = new Element("TranNo");//交易流水号
        TranNo.setText("");
        Element InsuCom = new Element("InsuCom");//保险公司代码
        InsuCom.setText("BX");
        Head.addContent(TranDate);
        Head.addContent(TranTime);
        Head.addContent(TranCom);
        Head.addContent(NodeNo);
        Head.addContent(TellerNo);
        Head.addContent(FuncFlag);
        Head.addContent(ChannelNo);
        Head.addContent(TranNo);
        Head.addContent(InsuCom);
        TranData.addContent(Head);
        
        // 创建元素 及 设置为子元素  报文头
        Element Body = new Element("Body");
        Element CifID = new Element("CifID");//客户号
        CifID.setText("");
        Body.addContent(CifID);
        TranData.addContent(Body);
		
		Document pXmlDoc=new Document(TranData);
		String  tReq  =  JdomUtil.toString(pXmlDoc);
		
		CopyOfTestWbeSphereMQ3 mq=new CopyOfTestWbeSphereMQ3();
		mq.sendInfoToCRPT(tReq);
	}
	
	
}
