package com.csii.test;



import java.io.IOException;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;

public class WbeSphereMQ2 { 

	public String  sendInfoToCRPT(String XMLDOC){
		//2033的错误
		System.out.println("+++++++扣款++++++");
		String content = new String();
		StringBuffer sbContent = new StringBuffer();
		sbContent.append("ISM Fixed V02.00YNHSBC_YBT                                                       YBT_CN_HUB                                                      ");
		sbContent.append("DEBITINSURANCE                                                                                                                                                                                                                                          ");
		sbContent.append("Z2017-10-20T10:20:28.000069 +08:00                                                                                                                                                                                                                                                                              ");
		sbContent.append("0000007890000000000000001290000004530000002070010101OH_SERVICE_HEADER               00860100000   INTBCNHSBC                                                                  0101000199000199YBT_CN_HUB                                                      DEBITINSURANCE                                                  01.0001.00                                                                                                                                                                                                                                                                                                           01000199");
		StringBuffer headContent = new StringBuffer();
		headContent.append("171020  ");//交易日期
		headContent.append("151430  ");//交易时间
		headContent.append("01");//银行代码
		headContent.append("0000012345");//网点代码
		headContent.append("000001234500000123450000012345");//柜员代码
		int L=(int) (Math.random()*10);
		int X=(int) (Math.random()*10);
		headContent.append("000001244500000123"+X+L);//交易流水号
		headContent.append("debit               ");//交易类型
		headContent.append("10        ");//交易渠道
		headContent.append("INBJ                ");//保险公司代码
		headContent.append("CNHSBC706009446     ");//客户号
		headContent.append("CNHSBC086009446050            ");//账号？
		headContent.append("201314              ");//扣款金额
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
			System.out.println("openOptionsArg:" + openOptionsArg);
			queue= manger.accessQueue(qName, openOptionsArg);//建立通道的连接
			System.out.println("消息通道建立成功");
			MQMessage message= new MQMessage();//要写入的消息
			message.characterSet=1208;
			message.encoding=546;
//			message.messageFlags= MQC.MQMT_REQUEST;
			message.format=MQC.MQFMT_STRING;
			message.replyToQueueManagerName=replyToQueueManager;
			message.replyToQueueName=replyToQueue;
			
			
			MQPutMessageOptions  putOp = new MQPutMessageOptions();
			System.out.println("qqqqqqqqqqqq");
			putOp.options= putOp.options+MQC.MQPMO_NEW_CORREL_ID;
			putOp.options= putOp.options+MQC.MQPMO_SYNCPOINT;
			System.out.println("options: "+putOp.options);
			message.writeString(content);// 将消息放入队列
			System.out.println("成功放入消息");
			queue.put(message, putOp);
			/*提交事务*/
			manger.commit();
			//System.out.println("请求消息发送成功 "+content+"\n correlationId: "+message.messageId);
			/*关闭请求队列*/
			//queue.close();
			System.out.println("准备接收回复+++++++++++++++");
			/**
			 * 接收回复
			 */
			/*以下选项可适合远程队列与本地队列*/    
            //int openOptions = MQConstants.MQOO_INQUIRE | MQConstants.MQOO_OUTPUT; //发送时使用  
            //int qOptioin = MQConstants.MQOO_INPUT_AS_Q_DEF | MQConstants.MQOO_OUTPUT; 接收时使用  
			//int openOptions = MQConstants.MQOO_INPUT_SHARED | MQConstants.MQOO_FAIL_IF_QUIESCING;
			int openOptions = MQConstants.MQOO_OUTPUT | MQConstants.MQOO_INPUT_SHARED;
			System.out.println("111111111");
			MQMessage messageRec= new MQMessage();//要读的队列的消息
//			messageRec.correlationId = message.messageId;
//			messageRec.characterSet=1381;
//			messageRec.encoding=1381;
//			messageRec.format=MQConstants.MQFMT_STRING;
			MQGetMessageOptions getOp = new MQGetMessageOptions();
			System.out.println("222222222");
			getOp.options = getOp.options +MQConstants.MQGMO_SYNCPOINT;
			getOp.options = getOp.options +MQConstants.MQGMO_WAIT;
			getOp.matchOptions = MQConstants.MQMO_MATCH_CORREL_ID;
			getOp.waitInterval= 30000;
			queueRec = manger.accessQueue(replyToQueue, openOptions);
			System.out.println("333333333");
			queueRec.get(messageRec, getOp);
			
			infoRec =  messageRec.readStringOfByteLength(messageRec.getMessageLength());
			System.out.println("成功接收回复的信息："+infoRec); 
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
	
}
	
	
	
	

