package com.example.bootkotlin


import org.hibernate.FlushMode
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.orm.hibernate3.SessionFactoryUtils
import org.springframework.orm.hibernate3.SessionHolder
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.support.TransactionSynchronizationManager


@RunWith(SpringJUnit4ClassRunner::class)
abstract class AbstractSessionTestCase {
    @Autowired
    lateinit var sessionFactory: SessionFactory
    private var session: Session? = null

    @Before
    @Throws(Exception::class)
    fun openSession() {

        session = SessionFactoryUtils.getSession(sessionFactory, true)
        session?.flushMode = FlushMode.MANUAL
        TransactionSynchronizationManager.bindResource(sessionFactory, SessionHolder(session))
    }

    @After
    @Throws(Exception::class)
    fun closeSession() {
        TransactionSynchronizationManager.unbindResource(sessionFactory);
        SessionFactoryUtils.releaseSession(session, sessionFactory);
    }
}