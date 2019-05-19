		.data

;***store the vector 
ReadBuffer1:	.space	120  ;store vector in byte
ReadPar1:	.word	0,ReadBuffer1,120
ReadBuffer2:	.space	120   ;store vector in byte
ReadPar2:   .word   0,ReadBuffer2,120


;***msg for output		
Prompt0:   .asciiz   "Add two vector:\n\n"
Prompt1:	   .asciiz   "vector1:(element seperated by space)\n"
Prompt2:   .asciiz    "vector2:(element seperated by space)\n"
Prompt3:   .asciiz   "vectorSum: "

;*** Data for Printf-Trap
PrintfFormat:  	.asciiz 	"%f  "             ;used to output the result
		            .align		4
PrintfPar:	    .word   	PrintfFormat
PrintfValue:	  .space		8
PrintfPar2:     .space    4

		.text
		.global		main
main:	

		;*** Prompt the msg
		addi    r1,r0,Prompt0
		sw      PrintfPar2,r1
		addi    r14,r0,PrintfPar2  
		trap		5
		
		;***get vector1 
		addi    r1,r0,Prompt1
		sw      PrintfPar2,r1
		addi    r14,r0,PrintfPar2  
		trap		5

		;*** call Trap-3 to read line vector1
		addi		r14,r0,ReadPar1
		trap		3
    
    ;***get vector2
    addi    r1,r0,Prompt2
		sw      PrintfPar2,r1
		addi    r14,r0,PrintfPar2  
		trap		5
    
    ;*** call Trap-3 to read line vector2
    addi		r14,r0,ReadPar2
		trap		3
    
    
    ;output the result msg
    addi    r1,r0,Prompt3
		sw      PrintfPar2,r1
		addi    r14,r0,PrintfPar2  
		trap		5
   
    
    ;*******************************************************
    ;calculate the vector sum one by one
    ;*******************************************************
    
    ;*****use f2 store vector1:f 20 store vector2
    ;*************************************************
    ;r1~r10,f1~f9  for vector1
    ;r11~r19,f11~f19 for vector2
    ;*************************************************
    ;*****change the byte into decimal
		;*** determine value
		
		addi		r2,r0,ReadBuffer1   ;Let r2 point to the first byte of vector1    
		addi    r12,r0,ReadBuffer2  ;Let r12 point to the first byte of vector2

BG:	
    ;******initial the register	
		addi		r1,r0,0            
		addi    r11,r0,0
		
		addi		r8,r0,10	;Decimal system
		addi    r18,r0,10 ;Decimal system
		
    addi    r6,r0,1   ;the decimal fraction part
    addi    r16,r0,1  
    
    addi    r22,r0,0 ;
   	
    movi2fp f1,r22     ;zero
    movi2fp f11,r22    
    
    movf    f3,f0     ;zero
    movf    f13,f0  
    
        
Loop1:		;*** reads integer part of vector1
		lbu		  r3,0(r2)
		seqi		r5,r3,10	
		bnez		r5,Loop2
		
		seqi    r5,r3,32  
		bnez		r5,Loop2  
			
		seqi    r5,r3,46  ;.-> read the decimal fraction part
		bnez    r5,Fraction1
		subi		r3,r3,48	;char->integer
		multu		r1,r1,r8	;Shift decimal
		add		  r1,r1,r3
		addi		r2,r2,1 	;increment pointer
		j		Loop1

Fraction1:  ;*****read the decimal part of vector2
    addi    r2,r2,1   ;increment pointer  
    lbu     r3,0(r2)
    seqi    r5,r3,10  ;LF->The element in vector1 is got
    bnez    r5,Loop2
    
    seqi    r5,r3,32  ;Space->The element in vector1 is got
		bnez		r5,Loop2
			
    subi    r3,r3,48
	  multu   r6,r6,r8  
	  movi2fp f6,r6
	  movi2fp f3,r3
	  divf    f3,f3,f6  ;f3 change to corresponding decimal
	  addf    f1,f1,f3  ;store the decimal part in f1
	  j       Fraction1 

Loop2:     ;*** reads integer part of vector1
    ;******
    movi2fp  f5,r1    
    cvti2f   f3,f5
    cvtf2d   f2,f3
    cvtf2d   f4,f1
    addd     f2,f2,f4 ;vector1 is in f2
    
    lbu		  r13,0(r12)
		seqi		r15,r13,10	
		bnez		r15,Finish  ;LF->Finished
		
		seqi    r15,r13,32  
		bnez		r15,SUM     ;The element in vector2 is got 
			
		seqi    r15,r13,46  ;.-> read the decimal fraction part
		bnez    r15,Fraction2
		subi		r13,r13,48	;char->integer
		multu		r11,r11,r18	;Shift decimal
		add		  r11,r11,r13
		addi		r12,r12,1 	;increment pointer
		j		Loop2
    
    
Fraction2:       ;*****read the decimal part of vector2
    addi    r12,r12,1   ;increment pointer  
    lbu     r13,0(r12)
    seqi    r15,r13,10  ;LF->EXIT
    bnez    r15,Finish
    
    seqi    r15,r13,32  ;Space
		bnez		r15,SUM
			
    subi    r13,r13,48
	  multu   r16,r16,r18  
	  movi2fp f16,r16
	  movi2fp f13,r13
	  divf    f13,f13,f16  ;f3 change to corresponding decimal
	  addf    f11,f11,f13  ;store the decimal part in f1
	  j       Fraction2 
   
   
SUM:    ;calculate the sum of elements<vector1,vector2>
    
    movi2fp  f15,r11    
    cvti2f   f13,f15
    cvtf2d   f12,f13
    cvtf2d   f14,f11
    addd     f12,f12,f14 ;vector2 is in f12
    
    ;increment pointer
    addi    r2,r2,1   ;increment pointer
    addi    r12,r12,1   ;increment pointer
    
    addd     f2,f2,f12 ;get and output the sum
    sd		   PrintfValue,f2
		addi		 r14,r0,PrintfPar
		trap		 5 
		j        BG       ;ready to calculate the next pair
		  
    
Finish: 	;***store the final result in f2.restore old register contents
    movi2fp  f15,r11    
    cvti2f   f15,f13
    cvtf2d   f12,f13
    cvtf2d   f14,f11
    addd     f12,f12,f14 ;vector2 is in f12
    
    addd     f2,f2,f12 ;get and output the last sum
    sd		   PrintfValue,f2
		addi		 r14,r0,PrintfPar
		trap		 5
		
		trap		0
