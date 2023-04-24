package com.example.crud.controller;

public class OrderController {
    
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

}
