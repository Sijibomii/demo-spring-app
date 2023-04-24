package com.example.crud.controller;

public class OrderController {

    // TASK: USE THE queryDslForPageListResult(QueryDslContext qdc, Integer pageNo, Integer pageSize) OF THIS SIGNATURE 

    // using the queryDsl function in BaseService
    // @PostMapping("page")
    // public MessageResult page(
    //         @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
    //         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
    //         @RequestParam(value = "lang", defaultValue = "CN") String lang
    // ) {
    //     //条件
    //     ArrayList<Predicate> predicates = new ArrayList<>(); 
    //     predicates.add(QAnnouncement.announcement.isShow.eq(true));
    //     predicates.add(QAnnouncement.announcement.lang.eq(lang));
    //     //排序 
    //     ArrayList<OrderSpecifier> orderSpecifiers = new ArrayList<>();
    //     orderSpecifiers.add(QAnnouncement.announcement.createTime.desc());
    //     //查   
    //     PageResult<Announcement> pageResult = announcementService.queryDsl(pageNo, pageSize, predicates, QAnnouncement.announcement, orderSpecifiers);
    //     List<Announcement> rlist = pageResult.getContent();
    //     for(int i = 0; i < rlist.size(); i++) {
    //     	rlist.get(i).setContent(null);
    //     }
    //     pageResult.setContent(rlist);
    //     return success(pageResult);
    // }
    

     // TASK: USE queryOneDsl(Predicate predicate, EntityPathBase<T> entityPathBase) METHOD


     
    // WithdrawRecordScreen is just a Model for validating data. Just has private field
    // WRITE FN TO PROCESS WITHHDRAWAL
    // HOW PREDICATE IS BEING USED
    // public MessageResult pageQuery(PageModel pageModel, WithdrawRecordScreen screen) {
    //     List<Predicate> predicates = new ArrayList<>();
    //     predicates.add(QWithdrawRecord.withdrawRecord.memberId.eq(QMember.member.id));

    //     if (screen.getMemberId() != null) {
    //         predicates.add(QWithdrawRecord.withdrawRecord.memberId.eq(screen.getMemberId()));
    //     }

    //     if ( !StringUtils.isEmpty(screen.getMobilePhone())){
    //         Member member = memberService.findByPhone(screen.getMobilePhone());
    //         predicates.add(QWithdrawRecord.withdrawRecord.memberId.eq(member.getId()));
    //     }

    //     if ( !StringUtils.isEmpty(screen.getOrderSn())){
    //         predicates.add(QWithdrawRecord.withdrawRecord.transactionNumber.eq(screen.getOrderSn()));
    //     }

    //     if (screen.getStatus() != null) {
    //         predicates.add(QWithdrawRecord.withdrawRecord.status.eq(screen.getStatus()));
    //     }

    //     if (screen.getIsAuto() != null) {
    //         predicates.add(QWithdrawRecord.withdrawRecord.isAuto.eq(screen.getIsAuto()));
    //     }

    //     if (!StringUtils.isEmpty(screen.getAddress())) {
    //         predicates.add(QWithdrawRecord.withdrawRecord.address.eq(screen.getAddress()));
    //     }

    //     if (!StringUtils.isEmpty(screen.getUnit())) {
    //         predicates.add(QWithdrawRecord.withdrawRecord.coin.unit.equalsIgnoreCase(screen.getUnit()));
    //     }

    //     if (!StringUtils.isEmpty(screen.getAccount())) {
    //         predicates.add(QMember.member.username.like("%" + screen.getAccount() + "%")
    //                 .or(QMember.member.realName.like("%" + screen.getAccount() + "%")));
    //     }

    //     Page<WithdrawRecordVO> pageListMapResult = withdrawRecordService.joinFind(predicates, pageModel);
    //     return success(pageListMapResult);
    // }


//     @RestController
// @RequestMapping("business/cancel-apply")
// public class BusinessCancelApplyController extends BaseController {
//     private static Logger logger = LoggerFactory.getLogger(BusinessCancelApplyController.class);
//     @Autowired
//     private BusinessCancelApplyService businessCancelApplyService;
//     @Autowired
//     private DepositRecordService depositRecordService;
//     @Autowired
//     private BusinessAuthApplyService businessAuthApplyService;
//     @Autowired
//     private MemberWalletService memberWalletService;
//     @Autowired
//     private MemberService memberService;
//     @Autowired
//     private LocaleMessageSourceService msService;

//     @PostMapping("page-query")
//     @RequiresPermissions("business:cancel-apply:page-query")
//     public MessageResult pageQuery(
//             PageModel pageModel,
//             @RequestParam(value = "account", required = false) String account,
//             @RequestParam(value = "status", required = false) CertifiedBusinessStatus status,
//             @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd") Date startDate,
//             @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd") Date endDate) {
//         List<BooleanExpression> predicates = new ArrayList<>();
//         if (!StringUtils.isEmpty(account)) { 
//             predicates.add(QBusinessCancelApply.businessCancelApply.member.username.like("%" + account + "%")
//                     .or(QBusinessCancelApply.businessCancelApply.member.mobilePhone.like("%" + account + "%"))
//                     .or(QBusinessCancelApply.businessCancelApply.member.email.like("%" + account + "%"))
//                     .or(QBusinessCancelApply.businessCancelApply.member.realName.like("%" + account + "%")));
//         }
//         predicates.add(QBusinessCancelApply.businessCancelApply.status.in(CANCEL_AUTH, RETURN_FAILED, RETURN_SUCCESS));
//         if (status != null) {
//             predicates.add(QBusinessCancelApply.businessCancelApply.status.eq(status));
//         }
//         if (startDate != null) {
//             predicates.add(QBusinessCancelApply.businessCancelApply.cancelApplyTime.goe(startDate));
//         }
//         if (endDate != null) {
//             predicates.add(QBusinessCancelApply.businessCancelApply.cancelApplyTime.loe(endDate));
//         }

//         Page<BusinessCancelApply> page = businessCancelApplyService.findAll(PredicateUtils.getPredicate(predicates), pageModel);

//         for (BusinessCancelApply businessCancelApply : page.getContent()) {
//             DepositRecord depositRecord = depositRecordService.findOne(businessCancelApply.getDepositRecordId());
//             businessCancelApply.setDepositRecord(depositRecord);
//         }
//         return success(page);
//     }

//     public class BusinessAuthController extends BaseAdminController {
//         @Autowired
//         private BusinessAuthDepositService businessAuthDepositService;
//         @Autowired
//         private CoinService coinService;
//         @Autowired
//         private BusinessAuthApplyService businessAuthApplyService;
    
//         @RequiresPermissions("business:auth:deposit:page")
//         @GetMapping("page")
//         public MessageResult getAll(PageModel pageModel, CommonStatus status) {
//             ArrayList<BooleanExpression> booleanExpressions = new ArrayList<>();
//             QBusinessAuthDeposit businessAuthDeposit = QBusinessAuthDeposit.businessAuthDeposit;
//             if (status != null) {
//                 booleanExpressions.add(businessAuthDeposit.status.eq(status));
//             }
//             Predicate predicate = PredicateUtils.getPredicate(booleanExpressions);
//             Page<BusinessAuthDeposit> depositPage = businessAuthDepositService.findAll(predicate, pageModel);
//             MessageResult result = MessageResult.success(); 
//             result.setData(depositPage);
//             return result;
//         }

//         @PostMapping("apply/page-query")
//         @RequiresPermissions("business-auth:apply:page-query")
//         public MessageResult page(
//                 PageModel pageModel,
//                 @RequestParam(value = "status", required = false) CertifiedBusinessStatus status,
//                 @RequestParam(value = "account", defaultValue = "") String account) {
//             List<BooleanExpression> lists = new ArrayList<>();
//             lists.add(QBusinessAuthApply.businessAuthApply.member.certifiedBusinessStatus.ne(CertifiedBusinessStatus.NOT_CERTIFIED));
//             if (!"".equals(account)) {
//                 lists.add(QBusinessAuthApply.businessAuthApply.member.username.like("%" + account + "%")
//                         .or(QBusinessAuthApply.businessAuthApply.member.mobilePhone.like(account + "%"))
//                         .or(QBusinessAuthApply.businessAuthApply.member.email.like(account + "%"))
//                         .or(QBusinessAuthApply.businessAuthApply.member.realName.like("%" + account + "%")));
//             }
//             if (status != null) {
//                 lists.add(QBusinessAuthApply.businessAuthApply.certifiedBusinessStatus.eq(status));
//             }
//             Page<BusinessAuthApply> page = businessAuthApplyService.page(PredicateUtils.getPredicate(lists), pageModel.getPageable());
//             return success(page);
    
//         }

// private Predicate getPredicate(ExchangeCoinScreen screen) {
//     ArrayList<BooleanExpression> booleanExpressions = new ArrayList<>();
//     QExchangeCoin qExchangeCoin = QExchangeCoin.exchangeCoin;
//     if (StringUtils.isNotBlank(screen.getCoinSymbol())) {
//         booleanExpressions.add(qExchangeCoin.coinSymbol.equalsIgnoreCase(screen.getCoinSymbol()));
//     }
//     if (StringUtils.isNotBlank(screen.getBaseSymbol())) {
//         booleanExpressions.add(qExchangeCoin.baseSymbol.equalsIgnoreCase(screen.getBaseSymbol()));
//     }
    
//     return PredicateUtils.getPredicate(booleanExpressions);
// }

// @RequiresPermissions("exchange:exchange-coin:page-query")
// @PostMapping("page-query")
// @AccessLog(module = AdminModule.EXCHANGE, operation = "分页查找币币交易手续费exchangeCoin")
// public MessageResult ExchangeCoinList(PageModel pageModel, ExchangeCoinScreen screen) {
//     if (pageModel.getProperty() == null) {
//         List<String> list = new ArrayList<>();
//         list.add("sort");
//         List<Sort.Direction> directions = new ArrayList<>();
//         directions.add(Sort.Direction.ASC);
//         pageModel.setProperty(list);
//         pageModel.setDirection(directions);
//     }
//     Predicate predicate = getPredicate(screen);
//     Page<ExchangeCoin> all = exchangeCoinService.findAll(predicate, pageModel.getPageable());

//     //远程RPC服务URL,获取当前交易引擎支持的币种
//     String serviceName = "SERVICE-EXCHANGE-TRADE";
//     String exchangeUrl = "http://" + serviceName + "/monitor/engines";
//     ResponseEntity<HashMap> result = restTemplate.getForEntity(exchangeUrl, HashMap.class);
//     Map<String, Integer> engineSymbols = (HashMap<String, Integer>)result.getBody();

//     for(ExchangeCoin item : all.getContent()) {
//         if(engineSymbols != null && engineSymbols.containsKey(item.getSymbol())) {
//             item.setEngineStatus(engineSymbols.get(item.getSymbol())); // 1: 运行中  2:暂停中
//         }else {
//             item.setEngineStatus(0); // 0:不可用
//         }
//         item.setCurrentTime(Calendar.getInstance().getTimeInMillis());
//     }
    
//     String marketServiceName = "bitrade-market";
//     String marketUrl = "http://" + marketServiceName + "/market/engines";
//     ResponseEntity<HashMap> marketResult = restTemplate.getForEntity(marketUrl, HashMap.class);
//     Map<String, Integer> marketEngineSymbols = (HashMap<String, Integer>)marketResult.getBody();
    
//     for(ExchangeCoin item : all.getContent()) {
//         // 行情引擎
//         if(marketEngineSymbols != null && marketEngineSymbols.containsKey(item.getSymbol())) {
//             item.setMarketEngineStatus(marketEngineSymbols.get(item.getSymbol()));
//         }else {
//             item.setMarketEngineStatus(0);
//         }
        
//         // 机器人
//         if(this.isRobotExists(item)) {
//             item.setExEngineStatus(1);
//         }else {
//             item.setExEngineStatus(0);
//         }
//     }
//     return success(all);
// }
}
