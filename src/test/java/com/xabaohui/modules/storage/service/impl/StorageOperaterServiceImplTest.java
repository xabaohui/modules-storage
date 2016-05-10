package com.xabaohui.modules.storage.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.xabaohui.modules.storage.bo.CheckStockBo;
import com.xabaohui.modules.storage.bo.InwarehouseBo;
import com.xabaohui.modules.storage.bo.OutwarehouseBo;
import com.xabaohui.modules.storage.constant.ConstantStorageCheckCheckTime;
import com.xabaohui.modules.storage.constant.ConstantStorageIoTaskBizType;
import com.xabaohui.modules.storage.dto.DistrBatchOutOfStockRequestDTO;
import com.xabaohui.modules.storage.dto.DistrBatchOutOfStockRequestDetail;
import com.xabaohui.modules.storage.dto.DistrBatchRequestDTO;
import com.xabaohui.modules.storage.dto.DistrBatchRequestDetail;
import com.xabaohui.modules.storage.dto.StorageCheckSnapDataDetail;
import com.xabaohui.modules.storage.dto.StorageInwarehouseDataDetail;
import com.xabaohui.modules.storage.entiry.StorageCheck;
import com.xabaohui.modules.storage.entiry.StorageCheckPlan;
import com.xabaohui.modules.storage.entiry.StorageCheckSnap;
import com.xabaohui.modules.storage.entiry.StoragePosition;
import com.xabaohui.modules.storage.service.StorageOperaterService;


@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class StorageOperaterServiceImplTest {
	@Resource
	private OutwarehouseBo outwarehouseBo;
	@Resource
	private InwarehouseBo inwarehouseBo;
	@Resource
	private CheckStockBo checkStockBo;
	@Resource
	private StorageOperaterService storageOperaterService;
	
	
	
	@Test
	public void testStorageInWareHouse() {//入库
		readyData();
	}

	@Test
	public void testFirstDistriApply() {//配货
		// 请求配货单(skuId ,amount) 10003 6
		List<DistrBatchRequestDetail> list = new ArrayList<DistrBatchRequestDetail>();
		list.add(new DistrBatchRequestDetail(10003, 6));
		DistrBatchRequestDTO request = new DistrBatchRequestDTO(100001, "配货出库",	list);
		//占用库存
		storageOperaterService.occupyStorageProduct(list);
		//chuku 
		storageOperaterService.firstDistriApply(request);
		
	}

	@Test
	public void testAgainDistriApply() {
	
	}

	@Test
	public void testErrorOutDetailFill() {
		//出错重配
		List<DistrBatchOutOfStockRequestDetail> list=new ArrayList<DistrBatchOutOfStockRequestDetail>();
		list.add(new DistrBatchOutOfStockRequestDetail("a1-11-22-06", 2, 10003));
		DistrBatchOutOfStockRequestDTO request=new DistrBatchOutOfStockRequestDTO(111111, 239, list);
		storageOperaterService.errorOutDetailFill(request);
	}

	@Test
	public void testOccupyStorageProduct() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveOccupyStorageProduct() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateStorageIoTask() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateStorageIoTaskToComplete() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateStorageCheckPlan() {//创建键盘点计划
		StorageCheckPlan checkPlan = storageOperaterService.createStorageCheckPlan(12001,"测试盘点计划");
		assertNotNull(checkPlan.getPlanId());
	}

	@Test
	public void testCreateStorageCheck() {//创建盘点
		StorageCheckPlan checkPlan = storageOperaterService.createStorageCheckPlan(12002, "测试盘点计划");
		StorageCheck check=storageOperaterService.createStorageCheck(checkPlan.getPlanId(), 1212122, 99, "测试盘点");
		assertNotNull(check.getCheckId());
	}

	@Test
	public void testFirstStorageCheck() {
		Integer positionId = readyCheckData().getPositionId();
		StorageCheckPlan checkPlan = storageOperaterService.createStorageCheckPlan(12003, "测试盘点计划");
		StorageCheck check=storageOperaterService.createStorageCheck(checkPlan.getPlanId(), 1212122, positionId, "测试盘点");
		
		//pid:166 10001:4 10003:2 10004:2库存数据
		//pid:166 10001:3 10003:2 10004:1盘点数据
		List<StorageCheckSnapDataDetail> listData=new ArrayList<StorageCheckSnapDataDetail>();
		listData.add(new StorageCheckSnapDataDetail(10010, 3));
		listData.add(new StorageCheckSnapDataDetail(10011, 2));
		listData.add(new StorageCheckSnapDataDetail(10012, 4));
		
		storageOperaterService.firstStorageCheck(listData, check.getCheckId());
		List<StorageCheckSnap> lisDataList = checkStockBo.findCheckSnapData( check.getCheckId(),ConstantStorageCheckCheckTime.FIRST_CHECK);
		
	}
	@Test
	public void testSecondStorageCheck() {
		/*Integer positionId = readyCheckData().getPositionId();
		StorageCheckPlan checkPlan = storageOperaterService.createStorageCheckPlan(12003, 3, "测试盘点计划");
		StorageCheck check=storageOperaterService.createStorageCheck(checkPlan.getPlanId(), 1212122, positionId, "测试盘点");*/
		
		//pid:166 10001:4 10003:2 10004:2库存数据
		//pid:166 10001:3 10003:2 10004:1盘点数据
		List<StorageCheckSnapDataDetail> listData=new ArrayList<StorageCheckSnapDataDetail>();
		listData.add(new StorageCheckSnapDataDetail(10010, 3));
		listData.add(new StorageCheckSnapDataDetail(10011, 2));
		listData.add(new StorageCheckSnapDataDetail(10012, 4));
		
		storageOperaterService.secondStorageCheck(listData,54);
		List<StorageCheckSnap> lisDataList = checkStockBo.findCheckSnapData(54,ConstantStorageCheckCheckTime.SECOND_CHECK);
	}

	@Test
	public void testAdjustMentCheckResult() {
		storageOperaterService.adjustMentCheckResult(222222, "盘点调整", 53, 10010, 6);
	}
	private StoragePosition readyCheckData() {
		// 添加库位记录
				StoragePosition position1 = inwarehouseBo.addStoragePosition("a1-11-22-07", 200, "测试库位");
				int operator = 444444;
				String memo = "测试入库";
		//10001 1
				//10003 p1 /2,2,3, p2/ 4 
				StorageInwarehouseDataDetail stDetail1 = new StorageInwarehouseDataDetail();
				stDetail1.setSkuId(10010);
				stDetail1.setAmount(1);
				stDetail1.setPositionId(position1.getPositionId());
				
				StorageInwarehouseDataDetail stDetail2 = new StorageInwarehouseDataDetail();
				stDetail2.setSkuId(10011);
				stDetail2.setAmount(2);
				stDetail2.setPositionId(position1.getPositionId());

				StorageInwarehouseDataDetail stDetail3 = new StorageInwarehouseDataDetail();
				stDetail3.setSkuId(10012);
				stDetail3.setAmount(2);
				stDetail3.setPositionId(position1.getPositionId());
				
				StorageInwarehouseDataDetail stDetail4 = new StorageInwarehouseDataDetail();
				stDetail4.setSkuId(10010);
				stDetail4.setAmount(3);
				stDetail4.setPositionId(position1.getPositionId());


				List<StorageInwarehouseDataDetail> listd = new ArrayList<StorageInwarehouseDataDetail>();
				listd.add(stDetail1);
				listd.add(stDetail2);
				listd.add(stDetail3);
				listd.add(stDetail4);
				
				
				// 插入数据
				storageOperaterService.storageInWareHouse(operator, memo,ConstantStorageIoTaskBizType.IN_WAREHOUSE,listd);
		return position1;
	}

	

	public void readyData(){
		// 添加库位记录
		StoragePosition position1 = inwarehouseBo.addStoragePosition("a1-11-22-03", 200, "测试库位");
		StoragePosition position2 = inwarehouseBo.addStoragePosition("a1-11-22-04", 200, "测试库位");
		StoragePosition position3 = inwarehouseBo.addStoragePosition("a1-11-22-05", 200, "测试库位");
		StoragePosition position4 = inwarehouseBo.addStoragePosition("a1-11-22-06", 200, "测试库位");		
		int operator = 10004;
		String memo = "测试入库";
//10001 1
		//10003 p1 /2,2,3, p2/ 4 
		StorageInwarehouseDataDetail stDetail1 = new StorageInwarehouseDataDetail();
		stDetail1.setSkuId(10001);
		stDetail1.setAmount(1);
		stDetail1.setPositionId(position1.getPositionId());
		
		StorageInwarehouseDataDetail stDetail2 = new StorageInwarehouseDataDetail();
		stDetail2.setSkuId(10003);
		stDetail2.setAmount(2);
		stDetail2.setPositionId(position1.getPositionId());

		StorageInwarehouseDataDetail stDetail3 = new StorageInwarehouseDataDetail();
		stDetail3.setSkuId(10003);
		stDetail3.setAmount(2);
		stDetail3.setPositionId(position2.getPositionId());
		
		StorageInwarehouseDataDetail stDetail4 = new StorageInwarehouseDataDetail();
		stDetail4.setSkuId(10003);
		stDetail4.setAmount(3);
		stDetail4.setPositionId(position3.getPositionId());
		
		StorageInwarehouseDataDetail stDetail5 = new StorageInwarehouseDataDetail();
		stDetail5.setSkuId(10003);
		stDetail5.setAmount(4);
		stDetail5.setPositionId(position4.getPositionId());


		List<StorageInwarehouseDataDetail> listd = new ArrayList<StorageInwarehouseDataDetail>();
		listd.add(stDetail1);
		listd.add(stDetail2);
		listd.add(stDetail3);
		listd.add(stDetail4);
		listd.add(stDetail5);
		
		
		// 插入数据
		storageOperaterService.storageInWareHouse(operator, memo,ConstantStorageIoTaskBizType.IN_WAREHOUSE,listd);
		// 占用库存
	}
}
